package org.example.mvc.service;

import org.example.configuration.GlobalConfig;
import org.example.mvc.domain.dto.UploadUserDTO;
import org.example.mvc.domain.dto.UserInfoDTO;
import org.example.mvc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private GlobalConfig globalConfig;

    @Autowired
    private UserRepository userRepository;

    public UserInfoDTO getUser(String userName){
        return userRepository.getUser(userName);
    }

    public void setUser(UploadUserDTO userDTO){
        userRepository.setUser(userDTO);
    }

}
