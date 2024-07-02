package org.example.mvc.domain.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.mvc.domain.entity.User;

@Getter
@Setter
public class UserRegisterDTO {
    private String userId;
    private String password;
    private String userName;
    private String phoneNumber;
    private String email;

    public UserRegisterDTO(){

    }

    public UserRegisterDTO(String userId, String password, String userName, String phoneNumber, String email){
        this.userId = userId;
        this.password = password;
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

}
