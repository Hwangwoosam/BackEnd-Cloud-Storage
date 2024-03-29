package org.example.mvc.domain.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class Metadata {
    private int fileSeq;
    private String userName;
    private String fileName;
    private boolean fileType;
    private boolean thumbnailCheck;
    private int fileSize;
    private String filePath;
    private int includeDir;
    private Date createTime;
}
