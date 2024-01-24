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
    private int fileSize;
    private String fileName;
    private String filePath;
    private String originalName;
    private Date createTime;

    public MetaDataDTO(){

    }

    public MetaDataDTO(int fileSeq, String userName, boolean thumbnailCheck
                            , int size, String fileName, String filePath,
                       String originalName, Date createTime){
        this.fileSeq = fileSeq;
        this.userName = userName;
        this.thumbnailCheck = thumbnailCheck;
        this.fileSize = size;
        this.fileName = fileName;
        this.filePath = filePath;
        this.originalName = originalName;
        this.createTime = createTime;
    }
}
