package org.example.mvc.service;

import org.example.configuration.GlobalConfig;
import org.example.configuration.exception.BaseException;
import org.example.configuration.http.BaseResponseCode;
import org.example.mvc.domain.dto.UploadUserDTO;
import org.example.mvc.domain.dto.UserInfoDTO;
import org.example.mvc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private GlobalConfig globalConfig;

    @Autowired
    private UserRepository userRepository;

    public void login(String userName){
        if(!IsRegisterUser(userName)){
            String uploadPath = globalConfig.getUploadFilePath();
            String folderPath = UUID.randomUUID().toString();
            File folder = new File(uploadPath + folderPath);

            if(!folder.mkdir()){
                throw new BaseException(BaseResponseCode.ERROR);
            }
            setUser(new UploadUserDTO(userName,uploadPath + folderPath));
        }
    }

    public boolean IsRegisterUser(String userName){
        UserInfoDTO userInfoDTO = getUser(userName);

        if(userInfoDTO == null) return false;

        return true;
    }

    public UserInfoDTO getUser(String userName){
        return userRepository.getUser(userName);
    }

    public void setUser(UploadUserDTO userDTO){
        userRepository.setUser(userDTO);
    }

}
