package org.example.mvc.domain.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@Getter
@Setter
public class MetaDataDTO {
    private int fileSeq;
    private String userName;
    private boolean thumbnailCheck;
    private int fileType;
    private int fileSize;
    private String fileName;
    private String filePath;
    private String originalName;
    private int includeDir;
    private Date createTime;

    public MetaDataDTO(){

    }

    public MetaDataDTO(int fileSeq, String userName, boolean thumbnailCheck,
                       int fileType, int size, String fileName, String filePath,
                       String originalName, int includeDir,Date createTime){
        this.fileSeq = fileSeq;
        this.userName = userName;
        this.thumbnailCheck = thumbnailCheck;
        this.fileType = fileType;
        this.fileSize = size;
        this.fileName = fileName;
        this.filePath = filePath;
        this.originalName = originalName;
        this.includeDir = includeDir;
        this.createTime = createTime;
    }
}
