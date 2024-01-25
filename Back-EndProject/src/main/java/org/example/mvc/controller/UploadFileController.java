package org.example.mvc.controller;

import org.example.configuration.GlobalConfig;
import org.example.configuration.exception.BaseException;
import org.example.configuration.http.BaseResponseCode;
import org.example.mvc.domain.dto.MetaDataDTO;
import org.example.mvc.domain.dto.UploadFileDTO;
import org.example.mvc.service.UploadFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/file")
public class UploadFileController {

    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private GlobalConfig globalConfig;
    @Autowired
    private UploadFileService uploadFileService;

    @RequestMapping("/save")
    public String save(@RequestParam("uploadFile") MultipartFile multipartFile, @RequestParam("userName") String userName, Model model) throws IOException{

        if(multipartFile == null || multipartFile.isEmpty()){
            throw new BaseException(BaseResponseCode.DATA_IS_NULL, new String[]{"업로드 파일"});
        }

        if(userName.isEmpty()){
            throw new BaseException(BaseResponseCode.DATA_IS_NULL, new String[]{"사용자 이름"});
        }

        logger.debug("save : {}",userName);
        model.addAttribute("userName",userName);

        String encodedUserName;
        try {
            encodedUserName = URLEncoder.encode(userName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Error encoding userName", e);
        }

        uploadFileService.fileSave(multipartFile,userName);
        return "redirect:/file/getList?userName=" + encodedUserName;
    }

    @GetMapping("/getList")
    public String getList(@RequestParam(name = "userName",required = false)String userName,Model model){
        List<MetaDataDTO> list = uploadFileService.getList();
        model.addAttribute("userName",userName);
        model.addAttribute("fileList",list);
        return "uploadFile/file_list.html";
    }

    @PostMapping("/delete/{fileSeq}")
    public String delete(@PathVariable int fileSeq){
        if(fileSeq < 0){
            throw new BaseException(BaseResponseCode.VALIDATE_REQUIRED,new String[]{"파일 번호"});
        }

        uploadFileService.delete(fileSeq);
        return "redirect:/file/getList";
    }

    @GetMapping("/download/{fileSeq}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable int fileSeq) throws IOException {
        if(fileSeq < 0){
            throw new BaseException(BaseResponseCode.VALIDATE_REQUIRED,new String[]{"파일 번호"});
        }

        return uploadFileService.downloadFile(fileSeq);
    }

}