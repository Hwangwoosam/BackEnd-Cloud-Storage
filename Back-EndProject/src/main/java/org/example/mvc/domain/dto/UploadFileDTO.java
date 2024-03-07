package org.example.mvc.domain.dto;

import lombok.Data;
import org.eclipse.tags.shaded.org.apache.xpath.operations.Mult;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UploadFileDTO {
    private String userName;
    private boolean fileType;
    private boolean thumbnailCheck;
    private int size;
    private String fileName;
    private String filePath;
    private int includeDir;

    public UploadFileDTO(){}

    public UploadFileDTO(String userName, boolean fileType, int size,
                         String fileName, String filePath,
                         boolean thumbnailPath, int includeDir){
        this.userName = userName;
        this.fileType = fileType;
        this.size = size;
        this.fileName = fileName;
        this.filePath = filePath;
        this.thumbnailCheck = thumbnailPath;
        this.includeDir = includeDir;
    }

}
