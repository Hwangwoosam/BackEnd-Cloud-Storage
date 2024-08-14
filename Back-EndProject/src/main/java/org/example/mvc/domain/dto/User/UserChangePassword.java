package org.example.mvc.domain.dto.User;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
public class UserChangePassword {
    @NotNull
    private String userId;

    @NotEmpty(message = "비밀번호는 필수 입력값입니다.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$", message = "비밀번호는 8글자 이상, 영문, 숫자, 특수문자(@$!%*#?&)를 사용하세요")
    private String nextPassword;

    @NotEmpty(message = "비밀번호 확인은 필수 입력값입니다.")
    private String nextPasswordRetype;

    @Autowired
    PasswordEncoder passwordEncoder;

    public UserChangePassword(){}

    public UserChangePassword(String userId, String nextPassword,String nextPasswordRetype){
        this.userId = userId;
        this.nextPassword = passwordEncoder.encode(nextPassword);
        this.nextPasswordRetype = passwordEncoder.encode(nextPasswordRetype);
    }
}
