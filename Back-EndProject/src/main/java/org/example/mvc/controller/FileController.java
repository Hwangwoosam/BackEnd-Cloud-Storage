package org.example.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class fileController {

    @GetMapping("fileList")
    public String fileList(){
        return "file_list.html";
    }
}
