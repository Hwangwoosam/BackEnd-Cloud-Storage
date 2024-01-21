package org.example.mvc.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Controller
public class BaseController {

    @RequestMapping("/fileUpload")
    public String fileUpload() {
        return "uploadFile/file_upload.html";
    }

    @GetMapping("/example1")
    public String example1(@RequestParam String id, @RequestParam String code, Model model){
        model.addAttribute("id",id);
        model.addAttribute("code",code);
        return "example1.jsp";
    }
}
