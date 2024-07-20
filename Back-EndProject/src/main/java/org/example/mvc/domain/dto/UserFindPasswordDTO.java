package org.example.mvc.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UserFindPasswordDTO {
    @Pattern(regexp = "^[A-Za-z0-9]+$" , message = "아이디는 영어 소문자와 숫자만 사용하여 4~12자리여야 합니다.")
    private String userId;
    private String phoneNumber;
    @Email(message = "유효한 이메일 형식이 아닙니다")
    private String email;

    public UserFindPasswordDTO() {}

    public UserFindPasswordDTO(String userId, String phoneNumber, String email){
        this.userId = userId;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
}
