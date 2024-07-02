package org.example.mvc.controller;

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
@Controller
public class loginController {

    @Autowired
    private UserService userService;

    @PostMapping("login")
    public String login(UserLoginDTO userLoginDto, HttpServletRequest request, RedirectAttributes rttr){


        return "file_list.html";
    }
    @PostMapping("checkDuplicateId")
    @ResponseBody
    public boolean checkDuplicatedId(Map<String, String> payload){
        String userId = payload.get("userId");
        System.out.println(userId);
        boolean isDuplicate = userService.checkDuplicateId(userId);
        return  isDuplicate;
    }

    @PostMapping("register")
    @ResponseBody
    public Map<String,Object> register(UserRegisterDTO user){
        Map<String,Object> response = new HashMap<>();
        System.out.println(user.getUserId());
        System.out.println(user.getUserName());
        System.out.println(user.getPassword());
        System.out.println(user.getEmail());
        try {
            userService.registerUser(user);
            response.put("success",true);
        }catch (Exception e){
            response.put("success",false);
            response.put("message",e.getMessage());
        }

        return response;
    }

    @GetMapping("registerPage")
    public String registerPage(){
        return "register.html";
    }

    @GetMapping("findIdPage")
    public String findIdPage(){
        return "findId.html";
    }

    @GetMapping("findPasswordPage")
    public String findPasswordPage(){
        return "findPassword.html";
    }
}
