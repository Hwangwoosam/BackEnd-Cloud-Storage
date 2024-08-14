package org.example.mvc.repository;

import org.apache.ibatis.annotations.Param;
import org.example.mvc.domain.dto.User.*;
import org.example.mvc.domain.entity.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository {
    int register(User user);
    UserInfoDTO findByUserId(String userId);
    UserInfoDTO findByUserName(String userName);
    int changePassword(User user);
}
