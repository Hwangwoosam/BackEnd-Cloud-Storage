package org.example.mvc.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameters;
import org.apache.commons.collections4.Predicate;
import org.example.configuration.GlobalConfig;
import org.example.configuration.exception.BaseException;
import org.example.configuration.http.BaseResponseCode;
import org.example.mvc.domain.dto.MetaDataDTO;
import org.example.mvc.domain.dto.UploadUserDTO;
import org.example.mvc.domain.dto.UserInfoDTO;
import org.example.mvc.service.UploadFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.collections4.CollectionUtils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

@Controller
@RequestMapping("/file")
public class UploadFileController {

    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private GlobalConfig globalConfig;
    @Autowired
    private UploadFileService uploadFileService;

    @GetMapping("/login")
    public String login(@RequestParam("userName") String userName){
        logger.debug("login: {}",userName);
        if(userName.isEmpty()){
            throw new BaseException(BaseResponseCode.DATA_IS_NULL, new String[]{"사용자 이름"});
        }

        UserInfoDTO userInfoDTO = uploadFileService.getUser(userName);
        if(userInfoDTO == null){
            String uploadPath = globalConfig.getUploadFilePath();
            String folderPath = UUID.randomUUID().toString();
            File folder = new File(uploadPath + folderPath);
            if(!folder.mkdir()){
                throw new BaseException(BaseResponseCode.ERROR);
            }
            uploadFileService.setUser(new UploadUserDTO(userName,uploadPath + folderPath));
            userInfoDTO = uploadFileService.getUser(userName);
        }
        String encodedName;
        try {
            encodedName = URLEncoder.encode(userInfoDTO.getUserName(),"UTF-8");
        }catch (UnsupportedEncodingException e){
            throw new RuntimeException("Error encoding userName", e);
        }

        return "redirect:/file/getList?userName="+encodedName + "&includeDir=0";
    }
    @PostMapping("/save")
    public String save(@RequestParam("uploadFile") MultipartFile multipartFile, @RequestParam("userName") String userName,
                       @RequestParam("includeDir") int includeDir,Model model) throws IOException{
        if(multipartFile == null || multipartFile.isEmpty()){
            throw new BaseException(BaseResponseCode.DATA_IS_NULL, new String[]{"업로드 파일"});
        }

        if(userName.isEmpty()){
            throw new BaseException(BaseResponseCode.DATA_IS_NULL, new String[]{"사용자 이름"});
        }

        logger.debug("save : {}",userName);
        logger.debug("save-includeDir: {}",includeDir);

        String encodedUserName;
        try {
            encodedUserName = URLEncoder.encode(userName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Error encoding userName", e);
        }

        model.addAttribute("userName",userName);
        model.addAttribute("includeDir",includeDir);
        uploadFileService.fileSave(multipartFile,userName,includeDir);
        return "redirect:/file/getList?userName=" + encodedUserName + "&includeDir=" + includeDir;
    }

    @GetMapping(value = "/getList")
    public String demo(@RequestParam String userName,@RequestParam(defaultValue = "0") int includeDir, Model model){
        List<MetaDataDTO> list = uploadFileService.getList(userName,-1);
        List<MetaDataDTO> curList = uploadFileService.getList(userName,includeDir);

        logger.debug("getList-include: {}",includeDir);

        List<MetaDataDTO> sideList = new ArrayList<>();
        List<Integer> check = new ArrayList<>();

        for(MetaDataDTO meta : list){
                if(check.contains(meta.getFileSeq())) continue;
                Collection<MetaDataDTO> c = CollectionUtils.select(list, new Predicate<MetaDataDTO>() {
                    @Override
                    public boolean evaluate(MetaDataDTO object) {
                        return object.getIncludeDir() == meta.getFileSeq();
                    }
                });

                if(!c.isEmpty()){
                    MetaDataDTO tmp = new MetaDataDTO(meta);
                    int size = meta.getFileSize();
                    for(MetaDataDTO sub : c){
                        check.add(sub.getFileSeq());
                        size += sub.getFileSize();
                        meta.getSubFiles().add(sub);
                    }
                    sideList.add(meta);
                }else{
                    sideList.add(meta);
                }
        }
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
        String encodedUserName;
        try {
            encodedUserName = URLEncoder.encode(userName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Error encoding userName", e);
        }

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