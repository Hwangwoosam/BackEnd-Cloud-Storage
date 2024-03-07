package org.example.mvc.repository;

import org.apache.ibatis.annotations.Param;
import org.example.mvc.domain.dto.MetaDataDTO;
import org.example.mvc.domain.dto.UploadFileDTO;
import org.example.mvc.domain.dto.UploadUserDTO;
import org.example.mvc.domain.dto.UserInfoDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Repository
public interface UploadFileRepository {

    UserInfoDTO getUser(String userName);

    void setUser(UploadUserDTO userDTO);

    void getFileInfo(MultipartFile multipartFile,String userName);
    void save(UploadFileDTO uploadFileParameter);
    List<MetaDataDTO> getList(@Param("userName") String userName,@Param("includeDir") int includeDir);
    MetaDataDTO get(@Param("userName") String userName,@Param("fileSeq") int fileSeq);

    void modifyFolderName(MetaDataDTO metadatauUpadteDTO);

    void delete(List<Integer> fileSeqs);

    ResponseEntity<byte[]> downloadFile(int fileSeq);

    List<String> checkFileName(@Param("includeDir") int includeDir);

}