package org.example.mvc.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.example.mvc.domain.entity.User;

@Getter
@Setter
public class UserRegisterDTO {

    @NotEmpty(message = "아이디는 필수 입력값입니다.")
    @Pattern(regexp = "^[A-Za-z0-9]+$" , message = "아이디는 영어 소문자와 숫자만 사용하여 4~12자리여야 합니다.")
    private String userId;

    @NotEmpty(message = "비밀번호는 필수 입력값입니다.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$", message = "비밀번호는 8글자 이상, 영문, 숫자, 특수문자(@$!%*#?&)를 사용하세요")
    private String password;

    @NotEmpty(message = "비밀번호 확인은 필수 입력값입니다.")
    private String passwordRetype;

    private String userName;
    private String phoneNumber;

    @Email(message = "유효한 이메일 형식이 아닙니다")
    private String email;

    public UserRegisterDTO(){}

    public UserRegisterDTO(String userId, String password, String userName, String phoneNumber, String email, String rootPath){
        this.userId = userId;
        this.password = password;
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

}
