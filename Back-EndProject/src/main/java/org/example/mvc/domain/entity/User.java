package org.example.mvc.domain.entity;


import lombok.Getter;
import org.example.mvc.domain.dto.User.UserRegisterDTO;

@Getter
public class User {
    private int id;
    private String userId;
    private String password;
    private String name;
    private String phoneNumber;
    private String email;
    private Long totalSize;
    private String rootPath;

    public User(UserRegisterDTO user,String encodedPassword ,String rootPath){
        this.userId = user.getUserId();
        this.password =  encodedPassword;
        this.name = user.getUserName();
        this.phoneNumber = user.getPhoneNumber();
        this.email = user.getEmail();
        this.totalSize = 0L;
        this.rootPath = rootPath;
    }

    public User(String userId,String nextPassword){
        this.userId = userId;
        this.password = nextPassword;
    }
}
