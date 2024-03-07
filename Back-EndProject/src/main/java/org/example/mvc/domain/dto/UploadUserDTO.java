package org.example.mvc.domain.dto;

public class UploadUserDTO {
    private String userName;
    private String folderPath;

    public UploadUserDTO(String userName, String folderPath){
        this.userName = userName;
        this.folderPath = folderPath;
    }
}
