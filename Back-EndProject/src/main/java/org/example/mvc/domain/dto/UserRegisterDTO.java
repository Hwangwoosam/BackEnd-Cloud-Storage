package org.example.mvc.domain.dto;

public class UserDTO {
    private int id;
    private String userId;
    private String password;
    private String userName;
    private String phoneNumber;
    private String email;
    private Long totalSize;

    public UserDTO(String userId, String password, String userName, String phoneNumber, String email){
        this.userId = userId;
        this.password = password;
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
}
