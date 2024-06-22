package org.example.mvc.controller;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@Controller
public class loginController {

//    @GetMapping("login")
//    public String login(){
//
//    }
    @GetMapping("register")
    public String home(){
        return "register.html";
    }

    @GetMapping("findId")
    public String findId(){
        return "findId.html";
    }

    @GetMapping("findPassword")
    public String findPassword(){
        return "findPassword.html";
    }
}
