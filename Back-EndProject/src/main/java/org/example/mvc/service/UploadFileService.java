package org.example.mvc.service;

import org.apache.catalina.User;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Predicate;
import org.apache.ibatis.annotations.Param;
import org.example.configuration.GlobalConfig;
import org.example.configuration.exception.BaseException;
import org.example.configuration.http.BaseResponseCode;
import org.example.mvc.domain.dto.MetaDataDTO;
import org.example.mvc.domain.dto.UploadFileDTO;
import org.example.mvc.domain.dto.UploadUserDTO;
import org.example.mvc.domain.dto.UserInfoDTO;
import org.example.mvc.repository.UploadFileRepository;
import org.example.mvc.repository.UserRepository;
import org.example.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipOutputStream;

//@Slf4j
@Service
public class UploadFileService {

    @Autowired
    private GlobalConfig globalConfig;
    @Autowired
    private UploadFileRepository uploadFileRepository;
    @Autowired
    private UserRepository userRepository;

    Logger logger = LoggerFactory.getLogger(getClass());

    public void fileSave(MultipartFile multipartFile,String userName,int includeDir){
        String uploadFilePath = userRepository.getUser(userName).getFolderPath();

        if(includeDir != 0){
            uploadFilePath = uploadFilePath + "/" + uploadFileRepository.get(userName,includeDir).getFileName();
        }

        String filename = Utils.checkDuplicateFileName(multipartFile.getOriginalFilename(),includeDir);
        String pathname = uploadFilePath + "/" + filename;

        try {
            UploadFileDTO parameter = new UploadFileDTO(userName,false,(int)multipartFile.getSize(),filename,
                    uploadFilePath,false,includeDir);
            save(parameter);

            File dest = new File(pathname);
            multipartFile.transferTo(dest);
        }catch (IllegalStateException | IOException e){
            logger.error("e",e);
        }

    }

    public void folderSave(String userName,String folderName,int includeDir){
        String uploadFilePath = userRepository.getUser(userName).getFolderPath();

        if(includeDir != 0){
            uploadFilePath = uploadFilePath + "/" + userRepository.getUser(userName).getFolderPath();
        }

        try{
            UploadFileDTO parameter = new UploadFileDTO(userName,true,0,folderName,uploadFilePath
                    ,false,includeDir);

            File folder = new File(uploadFilePath+ "/" + folderName);
            if(folder.exists()){
                return;
            }

            if(!folder.mkdir()){
                return;
            }
            save(parameter);
        }catch (IllegalStateException e){
            logger.error("e",e);
        }

    }

    public void save(UploadFileDTO metadataInsertDTO){
        uploadFileRepository.save(metadataInsertDTO);
    }

    public void modifyName(MetaDataDTO metadataUpdateDTO){
        uploadFileRepository.modifyName(metadataUpdateDTO);
    }

    public List<MetaDataDTO> getList(String userName,int includeDir){
        return uploadFileRepository.getList(userName,includeDir);
    }

    public MetaDataDTO get(String userName,int fileSeq){
        return uploadFileRepository.get(userName,fileSeq);
    }

    public void delete(String userName, int fileSeq){
        MetaDataDTO fileDTO = get(userName,fileSeq);
        String filePath = fileDTO.getFilePath() + "/" + fileDTO.getFileName();
        List<Integer> fileSeqs = new ArrayList<>();
        fileSeqs.add(fileSeq);

        if(fileDTO.getFileType() == 1){
            List<MetaDataDTO> subFiles = getList(userName,fileSeq);
            for(MetaDataDTO subFile : subFiles){
                if(subFile.getFileType() == 1){
                    delete(userName,subFile.getFileSeq());
                }else {
                    fileSeqs.add(subFile.getFileSeq());
                    String subFilePath = subFile.getFilePath() + "/" +subFile.getFileName();
                    File file = new File(subFilePath);
                    if (!file.exists()) {
                        throw new BaseException(BaseResponseCode.UPLOAD_FILE_IS_NULL);
                    }

                    if (!file.delete()) {
                        throw new BaseException(BaseResponseCode.ERROR);
                    }
                }
            }
        }

        File file = new File(filePath);
        if(!file.exists()){
            throw new BaseException(BaseResponseCode.UPLOAD_FILE_IS_NULL);
        }

        if(!file.delete()){
            throw new BaseException(BaseResponseCode.ERROR);
        }

        uploadFileRepository.delete(fileSeqs);
    }

    public ResponseEntity<byte[]> downloadFile(String userName,int fileSeq) throws IOException {
        MetaDataDTO fileInfo = get(userName,fileSeq);
        String encodedFileName = new String(fileInfo.getFileName().getBytes("UTF-8"), "ISO-8859-1") + ".zip";

        String filePath = fileInfo.getFilePath();
        Path path = Paths.get(filePath,fileInfo.getFileName());
        logger.debug("download Path: {}",path);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", encodedFileName);

        if(fileInfo.getFileType() == 1){
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ZipOutputStream zipOut = new ZipOutputStream(baos);
            Utils.compressDirectory(path,fileInfo.getFileName(),zipOut);
            zipOut.close();

            return new ResponseEntity<>(baos.toByteArray(),headers,HttpStatus.OK);
        }else {
            byte[] fileData = Files.readAllBytes(path);
            return new ResponseEntity<>(fileData,headers,HttpStatus.OK);
        }
    }

    public List<MetaDataDTO> getFolderStruct(String userName, int includeDir){
        List<MetaDataDTO> list = getList(userName,-1);

        List<MetaDataDTO> sideList = new ArrayList<>();
        List<Integer> check = new ArrayList<>();

        for(MetaDataDTO meta : list){
            logger.debug("msg: {}",meta.getFileSeq());
            if(check.contains(meta.getFileSeq())) continue;

            if(meta.getIncludeDir() != 0) {
                for (MetaDataDTO folder : sideList) {
                    if (folder.getFileSeq() == meta.getIncludeDir()) {
                        folder.getSubFiles().add(meta);
                        break;
                    }
                }
            }else{
                sideList.add(meta);
            }
        }

        return sideList;
    }
}