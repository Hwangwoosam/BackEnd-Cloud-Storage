package org.example.mvc.service;

//import org.example.configuration.GlobalConfig;
//import org.example.mvc.domain.dto.UserInfoDTO;
import org.example.configuration.GlobalConfiguration;
import org.example.mvc.domain.dto.User.*;
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

    public UserInfoDTO findByUserId(String userId){
        UserInfoDTO userInfoDTO = userRepository.findByUserId(userId);
        return userInfoDTO;
    }

    public UserInfoDTO findByName(String userName){
        UserInfoDTO userInfoDTO = userRepository.findByUserName(userName);
        return userInfoDTO;
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
            return false;
        }

        return true;
    }

//    public UserInfoDTO login(UserLoginDTO userLoginDTO){
//        return userRepository.login(userLoginDTO);
//    }
//

//
//    public Integer findIdByUserIdAndEmail(UserFindPasswordDTO userFindPasswordDTO){
//        return userRepository.findId(userFindPasswordDTO);
//    }
//
    public boolean changePassword(UserChangePassword userChangePassword){
        return userRepository.changePassword(userChangePassword) == 1;
    }
}
