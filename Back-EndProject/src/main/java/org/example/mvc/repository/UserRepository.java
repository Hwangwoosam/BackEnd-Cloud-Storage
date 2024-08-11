package org.example.mvc.repository;

import org.example.mvc.domain.dto.User.*;
import org.example.mvc.domain.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository {
    int register(User user);
    UserInfoDTO findByUserId(String userId);
    UserInfoDTO findByUserName(String userName);
//    UserInfoDTO login(UserLoginDTO userLoginDTO);
    int changePassword(UserChangePassword userChangePassword);
}
