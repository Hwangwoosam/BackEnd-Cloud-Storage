package org.example.mvc.service;

//import org.example.configuration.GlobalConfig;
//import org.example.mvc.domain.dto.UserInfoDTO;
import org.example.configuration.GlobalConfiguration;
import org.example.mvc.domain.dto.*;
import org.example.mvc.domain.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.example.mvc.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private GlobalConfiguration globalConfiguration;
    @Autowired
    private UserRepository userRepository;

    public boolean checkDuplicateId(String id){
        int cnt = userRepository.checkDuplicateId(id);
        System.out.println(cnt);
        if(cnt > 0) return true;
        return false;
    }

    @Transactional
    public boolean registerUser(UserRegisterDTO userDto){
        String folderName = UUID.randomUUID().toString();

        User user = new User(userDto,folderName);
        if(userRepository.register(user) == 0) return false;

        File file = new File(globalConfiguration.getUploadPath() + folderName);
        if(file.mkdir()){
            System.out.println("User Directory Created Success");
        }else{
            System.out.println("User Directory Created failed");
        }

        return true;
    }


    public boolean login(UserLoginDTO userLoginDTO){
        return userRepository.isRegister(userLoginDTO) == 1;
    }

    public String findUserIdByNameAndEmail(UserFindIdDTO userFindIdDTO){
        return userRepository.findUserId(userFindIdDTO);
    }

    public Integer findIdByUserIdAndEmail(UserFindPasswordDTO userFindPasswordDTO){
        return userRepository.findId(userFindPasswordDTO);
    }

    public boolean changePassword(UserChangePassword userChangePassword){
        return userRepository.changePassword(userChangePassword) == 1;
    }
}
