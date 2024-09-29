package org.example.mvc.controller;

import org.example.mvc.domain.dto.User.UserLoginDTO;
import org.example.mvc.domain.entity.FileList;
import org.example.mvc.service.UploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/file")
public class FileController {

    @Autowired
    private UploadFileService uploadFileService;

    @GetMapping("/getList")
    public String getFileList(Model model){
        UserLoginDTO userLoginDTO = (UserLoginDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<FileList> fileList = uploadFileService.getList(userLoginDTO.getId(),userLoginDTO.getRootPath());
        model.addAttribute("fileList",fileList);

        return "file_list.html";
    }


}
