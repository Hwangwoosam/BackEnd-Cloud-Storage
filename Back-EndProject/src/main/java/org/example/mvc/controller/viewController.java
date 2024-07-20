package org.example.mvc.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class viewController {

    @GetMapping("/loginPage")
    public String index(HttpServletRequest request){
        if(request.getSession().getAttribute("user") != null) return "redirect:/fileListPage";

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
