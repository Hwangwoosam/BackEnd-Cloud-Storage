package org.example.mvc.domain.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private List<MetaDataDTO> subFiles;

    public MetaDataDTO(MetaDataDTO meta){
        this.fileSeq = meta.getFileSeq();
        this.userName = meta.getUserName();
        this.thumbnailCheck =  meta.thumbnailCheck;
        this.fileType =meta.getFileType();
        this.fileSize = meta.getFileSize();
        this.fileName = meta.getFileName();
        this.filePath = meta.getFilePath();
        this.originalName = meta.getOriginalName();
        this.includeDir = meta.getIncludeDir();
        this.createTime = meta.getCreateTime();
        this.subFiles = new ArrayList<>();
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
        this.subFiles = new ArrayList<>();
    }

    public List<MetaDataDTO> getList(){
        List<MetaDataDTO> ret = new ArrayList<>(this.subFiles);
        return ret;
    }

    public void add(MetaDataDTO elem){
        this.subFiles.add(elem);
    }

    public void remove(MetaDataDTO elem){
        this.subFiles.remove(elem);
    }
}
