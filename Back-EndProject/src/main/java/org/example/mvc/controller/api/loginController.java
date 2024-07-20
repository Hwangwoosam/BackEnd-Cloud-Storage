package org.example.mvc.controller.api;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.example.configuration.GlobalConfiguration;
import org.example.mvc.domain.dto.*;
import org.example.mvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

@RestController
public class loginController {

    @Autowired
    private UserService userService;

    @PostMapping("login")
    @ResponseBody
    public Map<String,Object> login(@Valid @RequestBody UserLoginDTO userLoginDto , HttpServletRequest request){
        Map<String,Object> response = new HashMap<>();


        try{
            if(userService.login(userLoginDto)) {
                HttpSession session = request.getSession();
                session.setAttribute("user",userLoginDto.getUserId());
                response.put("success",true);
            }else{
                response.put("success",false);
                response.put("message","로그인 실패");
            }
        }catch (Exception e){
            response.put("success",false);
            response.put("message",e.getMessage());
        }

        return response;
    }
    @PostMapping("checkDuplicateId")
    @ResponseBody
    public boolean checkDuplicatedId(@RequestBody Map<String, String> userId){
        String user = userId.get("userId");
        System.out.println("checkId: " + user);
        boolean isDuplicate = userService.checkDuplicateId(user);

        return  isDuplicate;
    }

    @PostMapping("register")
    @ResponseBody
    public Map<String,Object> register(@Valid @RequestBody UserRegisterDTO user){
        Map<String,Object> response = new HashMap<>();

        try {
            userService.registerUser(user);
            response.put("success",true);
        }catch (Exception e){
            response.put("success",false);
            response.put("message",e.getMessage());
        }

        return response;
    }

    @PostMapping("findId")
    @ResponseBody
    public  Map<String,Object> findId(@Valid @RequestBody UserFindIdDTO userFindIdDTO){
        Map<String,Object> response = new HashMap<>();
        try {
            String foundId = userService.findUserIdByNameAndEmail(userFindIdDTO);

            if (foundId != null) {
                response.put("success", true);
                response.put("foundId", foundId);
            } else {
                response.put("success", false);
                response.put("message", "일치하는 정보가 없습니다.");
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "아이디 찾기 중 오류가 발생했습니다: " + e.getMessage());
        }

        return response;
    }

    @PostMapping("findPassword")
    @ResponseBody
    public  Map<String,Object> findPassword(@Valid @RequestBody UserFindPasswordDTO userFindPasswordDTO){
        Map<String,Object> response = new HashMap<>();
        try{
            Integer id = 0;
            id = userService.findIdByUserIdAndEmail(userFindPasswordDTO);
            System.out.println("id: " + id);
            if(id != 0){
                response.put("success",true);
                response.put("id",id);
            }else{
                response.put("success", false);
                response.put("message", "일치하는 정보가 없습니다.");
            }
        }catch (Exception e){
            response.put("success", false);
            response.put("message", "비밀번호 찾기 중 오류가 발생했습니다: " + e.getMessage());
        }
        return response;
    }

    @PostMapping("changePassword")
    @ResponseBody
    public  Map<String,Object> changePassword(@Valid @RequestBody UserChangePassword userChangePassword){
        Map<String,Object> response = new HashMap<>();
        System.out.println(userChangePassword.getId());
        try{
            if(userService.changePassword(userChangePassword)) {
                response.put("success", true);
            }else{
                response.put("success",false);
            }
        }catch (Exception e){
            response.put("success",false);
            response.put("message",e.getMessage());
        }

        return response;
    }
}
