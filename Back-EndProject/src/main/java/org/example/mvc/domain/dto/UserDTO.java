package org.example.mvc.domain.dto;

public class UserDTO {
    private int id;
    private String userId;
    private String password;
    private String userName;

    public UserDTO(int id, String userId, String password, String userName){
        this.id = id;
        this.userId = userId;
        this.password = password;
        this.userName = userName;
    }
}
