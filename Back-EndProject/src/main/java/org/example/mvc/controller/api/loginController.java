package org.example.mvc.controller.api;

import jakarta.servlet.http.HttpServletRequest;
import org.example.mvc.domain.dto.UserLoginDTO;
import org.example.mvc.domain.dto.UserRegisterDTO;
import org.example.mvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;


@SpringBootApplication
@RestController
public class loginController {

    @Autowired
    private UserService userService;

    @PostMapping("login")
    @ResponseBody
    public Map<String,Object> login(@RequestBody UserLoginDTO userLoginDto){
        Map<String,Object> response = new HashMap<>();

        try{
            if(userService.login(userLoginDto)) {
                response.put("success",true);
            }else{
                response.put("success",false);
                response.put("message","로그인 실패");
            }
        }catch (Exception e){
            response.put("success",false);
            response.put("message",e.getMessage());
        }

        return response;
    }
    @PostMapping("checkDuplicateId")
    @ResponseBody
    public boolean checkDuplicatedId(@RequestBody Map<String, String> userId){
        String user = userId.get("userId");
        System.out.println("checkId: " + user);
        boolean isDuplicate = userService.checkDuplicateId(user);

        return  isDuplicate;
    }

    @PostMapping("register")
    @ResponseBody
    public Map<String,Object> register(@RequestBody UserRegisterDTO user){
        Map<String,Object> response = new HashMap<>();

        try {
            userService.registerUser(user);
            response.put("success",true);
        }catch (Exception e){
            response.put("success",false);
            response.put("message",e.getMessage());
        }

        return response;
    }
}
