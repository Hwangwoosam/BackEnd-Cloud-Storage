package org.example.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class viewController {
    @GetMapping("/login")
    public String index(){
        return "../static/index.html";
    }

    @GetMapping("/registerPage")
    public String registerPage(){
        return "register.html";
    }

    @GetMapping("/findIdPage")
    public String findIdPage(){
        return "findId.html";
    }

    @GetMapping("/findPasswordPage")
    public String findPasswordPage(){
        return "findPassword.html";
    }

    @GetMapping("/fileListPage")
    public String fileListPage(){ return "file_list.html"; }
}
