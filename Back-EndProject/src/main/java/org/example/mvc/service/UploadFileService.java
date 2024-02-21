package org.example.mvc.service;

import org.apache.ibatis.annotations.Param;
import org.example.configuration.GlobalConfig;
import org.example.configuration.exception.BaseException;
import org.example.configuration.http.BaseResponseCode;
import org.example.mvc.domain.dto.MetaDataDTO;
import org.example.mvc.domain.dto.UploadFileDTO;
import org.example.mvc.repository.UploadFileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//@Slf4j
@Service
public class UploadFileService {

    @Autowired
    private GlobalConfig globalConfig;
    @Autowired
    private UploadFileRepository uploadFileRepository;

    Logger logger = LoggerFactory.getLogger(getClass());

    public void fileSave(MultipartFile multipartFile,String userName){
        String uploadFilePath = globalConfig.getUploadFilePath();
        int idxOfDot = multipartFile.getOriginalFilename().lastIndexOf(".");
        String prefix = multipartFile.getOriginalFilename().substring(idxOfDot + 1, multipartFile.getOriginalFilename().length());

        boolean thumbnailCheck = isImageFile(prefix);

        String filename = UUID.randomUUID().toString() + "." +  prefix;

        File folder = new File(uploadFilePath);

        if(!folder.isDirectory()){
            folder.mkdirs();
        }

        String pathname = uploadFilePath + filename;

        String originalFileName = checkDuplicateFileName(multipartFile.getOriginalFilename(),prefix);

        try {
            UploadFileDTO parameter = new UploadFileDTO();
            parameter.setUserName(userName);
            parameter.setFileType(false);
            parameter.setSize((int) multipartFile.getSize());
            parameter.setFileName(originalFileName);
            parameter.setFilePath(uploadFilePath);
            parameter.setOriginalName(filename);
            parameter.setThumbnailCheck(thumbnailCheck);

            save(parameter);

            File dest = new File(pathname);
            multipartFile.transferTo(dest);
        }catch (IllegalStateException | IOException e){
            logger.error("e",e);
        }

    }

    public void folderSave(String userName,String folderName){
        String uploadFilePath = globalConfig.getUploadFilePath();
        logger.debug("userName: {}",userName);
        logger.debug("folderName: {}",folderName);
        logger.debug("filePath: {}",uploadFilePath);
        String originalFolder = UUID.randomUUID().toString();
        try{
            UploadFileDTO parameter = new UploadFileDTO();
            parameter.setUserName(userName);
            parameter.setFileType(true);
            parameter.setSize(0);
            parameter.setFileName(folderName);
            parameter.setFilePath(uploadFilePath);
            parameter.setOriginalName(originalFolder);
            parameter.setThumbnailCheck(false);

            save(parameter);

            File folder = new File(uploadFilePath+originalFolder);
            if(folder.exists()){
                return;
            }

            if(!folder.mkdir()){
                return;
            }

        }catch (IllegalStateException e){
            logger.error("e",e);
        }

    }

    public void save(UploadFileDTO metadataInsertDTO){
        uploadFileRepository.save(metadataInsertDTO);
    }

    public void modifyFolderName(MetaDataDTO metadataUpdateDTO){
        uploadFileRepository.modifyFolderName(metadataUpdateDTO);
    }

    public List<MetaDataDTO> getList(String userName,int includeDir){
        return uploadFileRepository.getList(userName,includeDir);
    }

    public MetaDataDTO get(String userName,int fileSeq){
        return uploadFileRepository.get(userName,fileSeq);
    }

    public void delete(String userName, int fileSeq){
        MetaDataDTO fileDTO = get(userName,fileSeq);

        String filePath = fileDTO.getFilePath() + fileDTO.getOriginalName();

        File file = new File(filePath);
        if(!file.exists()){
            throw new BaseException(BaseResponseCode.UPLOAD_FILE_IS_NULL);
        }

        if(!file.delete()){
            throw new BaseException(BaseResponseCode.ERROR);
        }

        uploadFileRepository.delete(fileSeq);
    }

    private boolean isImageFile(String extension) {
        return Arrays.asList("jpg", "png", "jpeg", "gif").contains(extension.toLowerCase());
    }

    public ResponseEntity<byte[]> downloadFile(String userName,int fileSeq) throws IOException {
        logger.debug("userName: {}",userName);
        logger.debug("fileSeq: {}",fileSeq);
        MetaDataDTO fileInfo = get(userName,fileSeq);
        String filePath = fileInfo.getFilePath() + fileInfo.getOriginalName();
        Path path = Paths.get(filePath);

        byte[] fileData = Files.readAllBytes(path);

        String encodedFileName = new String(fileInfo.getFileName().getBytes("UTF-8"), "ISO-8859-1");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", encodedFileName);

        return new ResponseEntity<>(fileData, headers, HttpStatus.OK);
    }

    public String checkDuplicateFileName(String fileName,String prefix) {
        logger.debug("checkDup: {}", fileName);
        List<String> fileNames = uploadFileRepository.checkFileName();

        logger.debug("fileNames: {}", fileNames);


        String result = fileName;

        if (fileNames.contains(fileName)) {
            Pattern p = Pattern.compile("(.*)\\s\\(([0-9]+)\\)[.].*");
            Matcher m = p.matcher(fileName);

            String originalFileName = fileName.substring(0, fileName.lastIndexOf("."));

            if (m.find()) {
                originalFileName = m.group(1);
            }

            String dest = "";
            for (int i = 1; i < Integer.MAX_VALUE; i++) {
                dest = String.format("%s (%d).%s", originalFileName, i, prefix);
                logger.debug("dest: {}", dest);
                if (!fileNames.contains(dest)) break;
            }
            result = dest;
        }

        return result;
    }
}