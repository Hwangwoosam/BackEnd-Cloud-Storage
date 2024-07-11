package org.example.mvc.domain.entity;


import org.example.mvc.domain.dto.UserLoginDTO;
import org.example.mvc.domain.dto.UserRegisterDTO;

public class User {
    private int id;
    private String userId;
    private String password;
    private String userName;
    private String phoneNumber;
    private String email;
    private Long totalSize;
    private String rootPath;

    public User(UserRegisterDTO user, String rootPath){
        this.userId = user.getUserId();
        this.password = user.getPassword();
        this.userName = user.getUserName();
        this.phoneNumber = user.getPhoneNumber();
        this.email = user.getEmail();
        this.totalSize = 0L;
        this.rootPath = rootPath;
    }

    public User(UserLoginDTO user){
        this.userId = user.getUserId();
        this.password = user.getPassword();
    }
}
