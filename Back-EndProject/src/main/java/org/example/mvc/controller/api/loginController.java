package org.example.mvc.controller.api;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.example.mvc.domain.dto.User.*;
import org.example.mvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/login")
public class loginController {

    @Autowired
    private UserService userService;

    @PostMapping("checkDuplicateId")
    public ResponseEntity<Map<String,Object>> checkDuplicatedId(@RequestBody Map<String, String> userId){
        UserInfoDTO userInfoDTO = userService.findByUserId(userId.get("userId"));
        boolean isDuplicate = false;
        if(userInfoDTO != null) isDuplicate = true;

        Map<String,Object> response = new HashMap<>();
        response.put("isDuplicate",isDuplicate);

        return  ResponseEntity.ok(response);
    }

    @PostMapping("register")
    public ResponseEntity<Map<String,Object>> register(@Valid @RequestBody UserRegisterDTO user){
        Map<String,Object> response = new HashMap<>();
        try {
            userService.registerUser(user);
            response.put("success",true);
        }catch (Exception e){
            response.put("success",false);
            response.put("message",e.getMessage());
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping("findId")
    public  ResponseEntity<Map<String,Object>> findId(@Valid @RequestBody Map<String, String> input){
        Map<String,Object> response = new HashMap<>();

        String userName = input.get("userName");
        System.out.println(userName);
        try {
            UserInfoDTO userInfoDTO = userService.findByName(userName);

            if (userInfoDTO != null) {
                response.put("success", true);
                response.put("userId", userInfoDTO.getUserId());
            } else {
                response.put("success", false);
                response.put("message", "일치하는 정보가 없습니다.");
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "아이디 찾기 중 오류가 발생했습니다: " + e.getMessage());
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping("/findPassword")
    public ResponseEntity<Map<String,Object>> findPassword(@Valid @RequestBody Map<String, String> input){
        Map<String,Object> response = new HashMap<>();

        try{
            String userId = input.get("userId");
            UserInfoDTO userInfoDTO = userService.findByUserId(userId);

            if(userInfoDTO != null){
                response.put("success",true);
                response.put("userId",userInfoDTO.getUserId());
            }else{
                response.put("success", false);
                response.put("message", "일치하는 정보가 없습니다.");
            }
        }catch (Exception e){
            response.put("success", false);
            response.put("message", "비밀번호 찾기 중 오류가 발생했습니다: " + e.getMessage());
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("changePassword")
    public  ResponseEntity<Map<String,Object>> changePassword(@Valid @RequestBody UserChangePassword userChangePassword){
        Map<String,Object> response = new HashMap<>();

        try{
            if(!userChangePassword.getNextPassword().equals(userChangePassword.getNextPasswordRetype())){
                throw new Exception("Password is manipulated");
            }

            if(userService.changePassword(userChangePassword)) {
                response.put("success", true);
            }else{
                response.put("success",false);
            }
        }catch (Exception e){
            response.put("success",false);
            response.put("message",e.getMessage());
        }

        return ResponseEntity.ok(response);
    }
//
//    @GetMapping("logout")
//    public Map<String,Object> logout(HttpServletRequest request){
//        Map<String,Object> response = new HashMap<>();
//        try{
//            HttpSession session = request.getSession(false);
//            if(session != null){
//                session.invalidate();
//                response.put("success",true);
//                response.put("message","로그아웃 되었습니다.");
//            }else{
//                response.put("success",false);
//                response.put("message","로그인된 세션이 없습니다.");
//            }
//        }catch (Exception e){
//            response.put("success",false);
//            response.put("message","로그아웃 중 오류가 발생했습니다: " + e.getMessage());
//        }
//        return response;
//    }
}
