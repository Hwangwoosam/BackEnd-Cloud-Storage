package org.example.mvc.repository;

import org.example.mvc.domain.dto.MetaDataDTO;
import org.example.mvc.domain.entity.Metadata;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UploadFileRepository {
    List<MetaDataDTO> getList(int userId);
}