package org.example.mvc.repository;

import org.example.mvc.domain.dto.MetaDataDTO;
import org.example.mvc.domain.dto.UploadFileDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Repository
public interface UploadFileRepository {

    void getFileInfo(MultipartFile multipartFile,String userName);
    void save(UploadFileDTO uploadFileParameter);
    List<MetaDataDTO> getList(String userName);
    MetaDataDTO get(int fileSeq);
    void delete(int fileSeq);

    ResponseEntity<byte[]> downloadFile(int fileSeq);

    List<String> checkFileName();

}