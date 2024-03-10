package org.example.mvc.controller;

import org.example.configuration.GlobalConfig;
import org.example.configuration.exception.BaseException;
import org.example.configuration.http.BaseResponseCode;
import org.example.mvc.domain.dto.UploadUserDTO;
import org.example.mvc.domain.dto.UserInfoDTO;
import org.example.mvc.repository.UserRepository;
import org.example.mvc.service.UploadFileService;
import org.example.mvc.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.UUID;

@Controller
@RequestMapping("/user")
public class LoginController {

    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private GlobalConfig globalConfig;
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login(@RequestParam("userName") String userName){
        logger.debug("login: {}",userName);
        if(userName.isEmpty()){
            throw new BaseException(BaseResponseCode.DATA_IS_NULL, new String[]{"사용자 이름"});
        }

        UserInfoDTO userInfoDTO = userService.getUser(userName);
        if(userInfoDTO == null){
            String uploadPath = globalConfig.getUploadFilePath();
            String folderPath = UUID.randomUUID().toString();
            File folder = new File(uploadPath + folderPath);
            if(!folder.mkdir()){
                throw new BaseException(BaseResponseCode.ERROR);
            }
            userService.setUser(new UploadUserDTO(userName,uploadPath + folderPath));
            userInfoDTO = userService.getUser(userName);
        }
        String encodedName;
        try {
            encodedName = URLEncoder.encode(userInfoDTO.getUserName(),"UTF-8");
        }catch (UnsupportedEncodingException e){
            throw new RuntimeException("Error encoding userName", e);
        }

        return "redirect:/file/getList?userName="+encodedName + "&includeDir=0";
    }
}
