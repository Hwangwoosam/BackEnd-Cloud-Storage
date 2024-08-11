package org.example.mvc.domain.dto.User;


import lombok.Data;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
@Data
public class UserInfoDTO {
    private int id;
    private String userId;
    private String name;
    private String password;
    private String rootPath;
    private Long totalSize;

    UserInfoDTO(){}

    UserInfoDTO(int id,String userId ,String name, String password, String rootPath,Long totalSize){
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.password = password;
        this.rootPath = rootPath;
        this.totalSize = totalSize;
    }
}
