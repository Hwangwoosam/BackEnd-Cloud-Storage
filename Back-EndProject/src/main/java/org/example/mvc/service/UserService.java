package org.example.mvc.service;

import org.example.configuration.GlobalConfiguration;
import org.example.enums.UserRole;
import org.example.mvc.domain.dto.User.*;
import org.example.mvc.domain.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.example.mvc.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final GlobalConfiguration globalConfiguration;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(GlobalConfiguration globalConfiguration, UserRepository userRepository,PasswordEncoder passwordEncoder){
        this.globalConfiguration = globalConfiguration;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public UserInfoDTO findByUserId(String userId){
        UserInfoDTO userInfoDTO = userRepository.findByUserId(userId);
        return userInfoDTO;
    }

    public UserLoginDTO findByName(String userName){
        UserLoginDTO userLoginDTO = userRepository.findByUserName(userName);
        return userLoginDTO;
    }
    @Transactional
    public boolean registerUser(UserRegisterDTO userDto){
        String folderName = UUID.randomUUID().toString();

        User user = new User(userDto,passwordEncoder.encode(userDto.getPassword()),folderName);

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

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        logger.info("loadUserByUsername called with username: {}", username);
        UserInfoDTO userInfoDTO = this.userRepository.findByUserId(username);


        if(userInfoDTO == null){
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }

        List<GrantedAuthority> authorityList = new ArrayList<>();
        if("administer".equals(username)){
            authorityList.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
        }else{
            authorityList.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
        }

        return new UserLoginDTO(userInfoDTO,authorityList);
    }

    public boolean changePassword(UserChangePassword userChangePassword){
        User user = new User(userChangePassword.getUserId(),passwordEncoder.encode(userChangePassword.getNextPassword()));
        return userRepository.changePassword(user) == 1;
    }
}
