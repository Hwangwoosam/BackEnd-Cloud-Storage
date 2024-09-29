package org.example.mvc.domain.entity;

import lombok.Getter;
import org.example.enums.FileType;
import org.example.mvc.domain.dto.MetaDataDTO;

import java.util.HashSet;
import java.util.Set;


@Getter
public class FileList {
    private MetaDataDTO metadata;
    private Set<Integer> subFiles;

    public FileList(MetaDataDTO metadata){
        this.metadata = metadata;
        if(FileType.fromCode(metadata.getFileType()) == FileType.valueOf("FOLDER")){
            this.subFiles = new HashSet<>();
        }else{
            this.subFiles = null;
        }
    }

    public boolean addSubFile(Integer subFileId){
        return subFiles.add(subFileId);
    }

    public boolean removeSubFile(Integer subFileId){
        return subFiles.remove(subFileId);
    }
}
