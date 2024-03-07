package org.example.mvc.domain.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UserInfoDTO {
    private int userId;
    private String userName;
    private String folderPath;

    public UserInfoDTO(int userId,String userName, String folderPath){
        this.userId = userId;
        this.userName = userName;
        this.folderPath = folderPath;
    }
}
