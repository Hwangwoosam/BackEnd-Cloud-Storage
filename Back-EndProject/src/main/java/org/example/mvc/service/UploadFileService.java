package org.example.mvc.service;

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
        String prefix = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf(".") + 1, multipartFile.getOriginalFilename().length());

        boolean thumbnailCheck = isImageFile(prefix);

        String filename = UUID.randomUUID().toString() + "." +  prefix;

        File folder = new File(uploadFilePath);

        if(!folder.isDirectory()){
            folder.mkdirs();
        }

        String pathname = uploadFilePath + filename;

        try{
            UploadFileDTO parameter = new UploadFileDTO();
            parameter.setUserName(userName);
            parameter.setFileType(false);
            parameter.setSize((int)multipartFile.getSize());
            parameter.setFileName(multipartFile.getOriginalFilename());
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
    public void save(UploadFileDTO metadataInsertDTO){
        uploadFileRepository.save(metadataInsertDTO);
    }

    public List<MetaDataDTO> getList(){
        return uploadFileRepository.getList();
    }

    public MetaDataDTO get(int fileSeq){
        return uploadFileRepository.get(fileSeq);
    }

    public void delete(int fileSeq){
        MetaDataDTO fileDTO = get(fileSeq);

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

    public ResponseEntity<byte[]> downloadFile(int fileSeq) throws IOException {
        MetaDataDTO fileInfo = get(fileSeq);
        String filePath = fileInfo.getFilePath() + fileInfo.getOriginalName();
        Path path = Paths.get(filePath);

        byte[] fileData = Files.readAllBytes(path);

        String encodedFileName = new String(fileInfo.getFileName().getBytes("UTF-8"), "ISO-8859-1");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", encodedFileName);

        return new ResponseEntity<>(fileData, headers, HttpStatus.OK);
    }
}