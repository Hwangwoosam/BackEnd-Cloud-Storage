package org.example.mvc.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.example.configuration.GlobalConfig;
import org.example.configuration.exception.BaseException;
import org.example.configuration.http.BaseResponse;
import org.example.configuration.http.BaseResponseCode;
import org.example.mvc.domain.dto.MetaDataDTO;
import org.example.mvc.domain.dto.UploadFileDTO;
import org.example.mvc.service.UploadFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api")
@Tag(name="파일 업로드 API")
public class UploadFileApiController {
        Logger logger = LoggerFactory.getLogger(getClass());

        @Autowired
        private GlobalConfig globalConfig;

        @Autowired
        private UploadFileService uploadFileService;

        @PostMapping("/login")
        public ResponseEntity<Map<String, String>> login(@RequestBody String userName) {
            Map<String, String> response = new HashMap<>();

            logger.debug("login api : {}",userName);
            response.put("result", "success");
            return ResponseEntity.ok(response);
        }

//        @GetMapping("/getList")
//        @ResponseBody
//        public ResponseEntity<List<MetaDataDTO>> getList(@PathVariable String userName){
//            logger.debug("api-getList : {}",userName);
//            List<MetaDataDTO> list = uploadFileService.getList(userName);
//            return new ResponseEntity<>(list, HttpStatus.OK);
//        }

        @PostMapping("/save")
        @Operation(summary = "업로드", description = "")
        public BaseResponse<Boolean> save(@RequestPart("uploadFile")MultipartFile multipartFile, @RequestPart("userName") String userName){

            if(multipartFile == null || multipartFile.isEmpty()){
                throw new BaseException(BaseResponseCode.DATA_IS_NULL, new String[]{"업로드 파일"});
            }

            if(userName.isEmpty()){
                throw new BaseException(BaseResponseCode.DATA_IS_NULL, new String[]{"사용자 이름"});
            }

            logger.debug("api-save : {}",userName);

            uploadFileService.fileSave(multipartFile,userName);

            return new BaseResponse<Boolean>(true);
        }

        @PostMapping("/file/save")
        public ResponseEntity<String> handleFileUpload(
                @RequestParam("uploadFile") MultipartFile file,
                @RequestParam("userName") String userName
        ) {
            System.out.println("Received file: " + file.getOriginalFilename() + " from user: " + userName);

            return ResponseEntity.ok().body("File uploaded successfully.");
        }

}
