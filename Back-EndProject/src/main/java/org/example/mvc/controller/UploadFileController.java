package org.example.mvc.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameters;
import org.example.configuration.GlobalConfig;
import org.example.configuration.exception.BaseException;
import org.example.configuration.http.BaseResponseCode;
import org.example.mvc.domain.dto.MetaDataDTO;
import org.example.mvc.service.UploadFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

import static org.example.utils.Utils.encodingUrl;

@Controller
@RequestMapping("/file")
public class UploadFileController {

    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private GlobalConfig globalConfig;
    @Autowired
    private UploadFileService uploadFileService;

    @PostMapping("/uploadFile")
    public String save(@RequestParam("uploadFile") MultipartFile multipartFile, @RequestParam("userName") String userName,
                       @RequestParam("includeDir") int includeDir,Model model) throws IOException{
        if(multipartFile == null || multipartFile.isEmpty()){
            throw new BaseException(BaseResponseCode.DATA_IS_NULL, new String[]{"업로드 파일"});
        }

        if(userName.isEmpty()){
            throw new BaseException(BaseResponseCode.DATA_IS_NULL, new String[]{"사용자 이름"});
        }

        model.addAttribute("userName",userName);
        model.addAttribute("includeDir",includeDir);

        logger.debug("save : {}",userName);
        logger.debug("save-includeDir: {}",includeDir);

        uploadFileService.fileSave(multipartFile,userName,includeDir);

        String encodedUserName = encodingUrl(userName);
        return "redirect:/file/getList?userName=" + encodedUserName + "&includeDir=" + includeDir;
    }

    @GetMapping(value = "/getList")
    public String demo(@RequestParam String userName,@RequestParam(defaultValue = "0") int includeDir, Model model){
        List<MetaDataDTO> curList = uploadFileService.getList(userName,includeDir);
        List<MetaDataDTO> sideList = uploadFileService.getFolderStruct(userName,includeDir);

        logger.debug("getList-include: {}",includeDir);

        model.addAttribute("includeDir",includeDir);
        model.addAttribute("userName", userName);
        model.addAttribute("sideList",sideList);
        model.addAttribute("curList",curList);
        return "uploadFile/file_list.html";
    }

    @PostMapping("/createFolder")
    @Operation(summary = "폴더 생성", description = "폴더 생성 API")
    @Parameters
    public String createFolder(@RequestParam("folderName") String folderName, @RequestParam("userName") String userName,
                               @RequestParam("includeDir") int includeDir,Model model){

        if(folderName.isEmpty()){
            throw new BaseException(BaseResponseCode.DATA_IS_NULL, new String[]{"폴더 이름"});
        }

        uploadFileService.folderSave(userName,folderName,includeDir);

        String encodedUserName;
        try {
            encodedUserName = URLEncoder.encode(userName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Error encoding userName", e);
        }
        model.addAttribute("userName",userName);
        model.addAttribute("includeDir",includeDir);

        return "redirect:/file/getList?userName=" + encodedUserName + "&includeDir=" + includeDir;
    }

    @PostMapping("/delete/{fileSeq}")
    public String delete(@RequestParam int fileSeq,@RequestParam String userName,@RequestParam int includeDir,Model model){
        logger.debug("delete : {}",fileSeq);
        if(fileSeq < 0){
            throw new BaseException(BaseResponseCode.VALIDATE_REQUIRED,new String[]{"파일 번호"});
        }

        uploadFileService.delete(userName,fileSeq);
        String encodedUserName = encodingUrl(userName);

        model.addAttribute("userName",userName);
        model.addAttribute("includeDir",includeDir);

        return "redirect:/file/getList?userName=" + encodedUserName +"&includeDir=" + includeDir;
    }

    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadFile(@RequestParam("fileSeq") int fileSeq,@RequestParam("userName") String userName) throws IOException {
        logger.debug("download: {}",fileSeq + " " + userName);
        if(fileSeq < 0){
            throw new BaseException(BaseResponseCode.VALIDATE_REQUIRED,new String[]{"파일 번호"});
        }

        return uploadFileService.downloadFile(userName,fileSeq);
    }


}