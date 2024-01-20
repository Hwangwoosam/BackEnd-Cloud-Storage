package org.example.mvc.domain.dto;

import lombok.Data;

@Data
public class MetadataDTO {
    private int fileSeq;
    private String userName;
    private boolean fileType;
    private boolean thumbnailCheck;
    private int size;
    private String fileName;
    private String filePath;
    private String originalPath;

    public MetadataDTO(){}

    public MetadataDTO(String userName, boolean fileType, int size, String fileName, String filePath,String originalPath,boolean thumbnailPath){
        this.userName = userName;
        this.fileType = fileType;
        this.size = size;
        this.fileName = fileName;
        this.filePath = filePath;
        this.originalPath = originalPath;
        this.thumbnailCheck = thumbnailPath;
    }

}
