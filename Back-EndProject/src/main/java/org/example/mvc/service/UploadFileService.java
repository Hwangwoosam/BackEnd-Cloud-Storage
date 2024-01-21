package org.example.mvc.service;

import org.example.mvc.domain.dto.MetadataInsertDTO;
import org.example.mvc.domain.dto.UploadFileDTO;
import org.example.mvc.repository.UploadFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//@Slf4j
@Service
public class UploadFileService {

    @Autowired
    private UploadFileRepository uploadFileRepository;

    public void save(MetadataInsertDTO metadataInsertDTO){
        uploadFileRepository.save(metadataInsertDTO);
    }

    public List<UploadFileDTO> getUploadFileList(){
        return uploadFileRepository.getUploadFileList();
    }

    public void delete(int fileSeq){
        uploadFileRepository.delete(fileSeq);
    }
}