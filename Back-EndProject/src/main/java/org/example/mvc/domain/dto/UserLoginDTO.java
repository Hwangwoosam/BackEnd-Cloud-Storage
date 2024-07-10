package org.example.mvc.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginDTO {
    private String userId;
    private String password;

    public UserLoginDTO() {};
    public UserLoginDTO(String userId, String password){
        this.userId = userId;
        this.password = password;
    }
}
