package org.example.mvc.domain.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Getter
public class MetaDataDTO {
    private int fileSeq;
    private int userId;
    private String fileName;
    private int fileType;
    private int fileSize;
    private String filePath;
    private int includeDir;
    private Date createTime;

    public MetaDataDTO(MetaDataDTO meta){
        this.fileSeq = meta.getFileSeq();
        this.userId = meta.getUserId();
        this.fileName = meta.getFileName();
        this.fileType =meta.getFileType();
        this.fileSize = meta.getFileSize();
        this.filePath = meta.getFilePath();
        this.includeDir = meta.getIncludeDir();
        this.createTime = meta.getCreateTime();
    }

    public MetaDataDTO(int fileSeq, int userId, int fileType, int size,
                       String fileName, String filePath, int includeDir,Date createTime){
        this.fileSeq = fileSeq;
        this.userId = userId;
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileSize = size;
        this.filePath = filePath;
        this.includeDir = includeDir;
        this.createTime = createTime;
    }
}
