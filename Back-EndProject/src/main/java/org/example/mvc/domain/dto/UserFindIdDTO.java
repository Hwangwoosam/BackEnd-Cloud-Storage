package org.example.mvc.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
public class UserFindIdDTO {

    private String userName;
    private String phoneNumber;
    @Email(message = "유효한 이메일 형식이 아닙니다")
    private String email;

    public UserFindIdDTO(){}

    public UserFindIdDTO(String userName, String phoneNumber, String email){
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
}
