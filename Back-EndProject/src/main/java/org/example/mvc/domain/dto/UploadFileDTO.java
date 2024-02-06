package org.example.mvc.domain.dto;

import lombok.Data;

@Data
public class UploadFileDTO {
    private String userName;
    private boolean fileType;
    private boolean thumbnailCheck;
    private int size;
    private String fileName;
    private String filePath;
    private String originalName;
    private int includeDir;

    public UploadFileDTO(){}

    public UploadFileDTO(String userName, boolean fileType, int size,
                         String fileName, String filePath, String originalName,
                         boolean thumbnailPath, int includeDir){
        this.userName = userName;
        this.fileType = fileType;
        this.size = size;
        this.fileName = fileName;
        this.filePath = filePath;
        this.originalName = originalName;
        this.thumbnailCheck = thumbnailPath;
        this.includeDir = includeDir;
    }

}
