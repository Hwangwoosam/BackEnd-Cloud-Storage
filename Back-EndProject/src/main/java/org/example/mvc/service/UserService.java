package org.example.mvc.service;

//import org.example.configuration.GlobalConfig;
//import org.example.mvc.domain.dto.UserInfoDTO;
import org.example.mvc.domain.dto.UserLoginDTO;
import org.example.mvc.domain.dto.UserRegisterDTO;
import org.example.mvc.domain.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.example.mvc.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

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
        //id, password 유효성 검사
        User user = new User(userDto);
        if(userRepository.register(user) == 0) return false;
        return true;
    }


    public boolean login(UserLoginDTO userLoginDTO){
        return userRepository.isRegister(userLoginDTO) == 1;
    }
//
//    public boolean IsRegisterUser(String userName){
//        UserInfoDTO userInfoDTO = getUser(userName);
//
//        if(userInfoDTO == null) return false;
//
//        return true;
//    }
//
//    public UserInfoDTO getUser(String userName){
//        return userRepository.getUser(userName);
//    }
//
//    public void setUser(UploadUserDTO userDTO){
//        userRepository.setUser(userDTO);
//    }
//
}
