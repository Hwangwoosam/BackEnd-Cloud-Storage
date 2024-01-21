package org.example.mvc.controller;

import org.example.configuration.GlobalConfig;
import org.example.configuration.exception.BaseException;
import org.example.configuration.http.BaseResponseCode;
import org.example.mvc.domain.dto.MetadataInsertDTO;
import org.example.mvc.domain.dto.UploadFileDTO;
import org.example.mvc.service.UploadFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
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
    public String save(@RequestParam("uploadFile") MultipartFile multipartFile, @RequestParam("userName") String userName) throws IOException{
        if(multipartFile == null || multipartFile.isEmpty()){
            throw new BaseException(BaseResponseCode.DATA_IS_NULL);
        }

        if(userName.isEmpty()){
            throw new BaseException(BaseResponseCode.DATA_IS_NULL, new String[]{"사용자 이름"});
        }

        String uploadFilePath = globalConfig.getUploadFilePath();
        String prefix = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf(".") + 1, multipartFile.getOriginalFilename().length());

        boolean thumbnailCheck = false;
        if(prefix.equals("jpg") || prefix.equals("png") || prefix.equals("jpeg") || prefix.equals("gif")) thumbnailCheck = true;

        String filename = UUID.randomUUID().toString() + "." +  prefix;

        File folder = new File(uploadFilePath);

        if(!folder.isDirectory()){
            folder.mkdirs();
        }

        String pathname = uploadFilePath + filename;

        try{
            MetadataInsertDTO parameter = new MetadataInsertDTO();
            parameter.setUserName(userName);
            parameter.setFileType(false);
            parameter.setSize((int)multipartFile.getSize());
            parameter.setFileName(multipartFile.getOriginalFilename().substring(0,multipartFile.getOriginalFilename().lastIndexOf(".")));
            parameter.setFilePath(uploadFilePath + multipartFile.getOriginalFilename());
            parameter.setOriginalPath(pathname);
            parameter.setThumbnailCheck(thumbnailCheck);
            uploadFileService.save(parameter);

            File dest = new File(pathname);
            multipartFile.transferTo(dest);
        }catch (IllegalStateException | IOException e){
            logger.error("e",e);
        }
        return "redirect:/file/getList";
    }

    @GetMapping("/getList")
    public String getUploadFileList(Model model){
        List<UploadFileDTO> list = uploadFileService.getUploadFileList();
        model.addAttribute("fileList",list);
        return "uploadFile/file_list.html";
    }

    @PostMapping("/delete/{fileSeq}")
    public String delete(@PathVariable int fileSeq){


        uploadFileService.delete(fileSeq);
        return "redirect:/file/getList";
    }

}