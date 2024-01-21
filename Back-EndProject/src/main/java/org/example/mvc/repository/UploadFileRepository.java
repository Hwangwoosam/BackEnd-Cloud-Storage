package org.example.mvc.repository;

import org.example.mvc.domain.dto.MetadataInsertDTO;
import org.example.mvc.domain.dto.UploadFileDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UploadFileRepository {

    void save(MetadataInsertDTO uploadFileParameter);

    List<UploadFileDTO> getUploadFileList();

    void delete(int fileSeq);
}