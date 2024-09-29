package org.example.mvc.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.example.mvc.domain.dto.User.UserLoginDTO;
import org.example.mvc.domain.entity.FileList;
import org.example.mvc.service.UploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ViewController {

    @Autowired
    private UploadFileService uploadFileService;

    @GetMapping("/loginPage")
    public String index(HttpServletRequest request){
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
    public String fileListPage(Model model){
        UserLoginDTO userLoginDTO = (UserLoginDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("totalSize",userLoginDTO.getTotalSize());
        model.addAttribute("includeDir",0);
        return "file_list.html";
    }
}
