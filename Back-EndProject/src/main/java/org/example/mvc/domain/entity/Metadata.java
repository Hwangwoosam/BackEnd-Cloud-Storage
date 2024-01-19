package org.example.mvc.domain.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
public class Metadata {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int fileSeq;
    private String userName;
    private boolean fileType;
    private int size;
    private Date createTime;
    private String fileName;
    private String filePath;
    private String originalPath;
    private String thumbnailPath;

}
