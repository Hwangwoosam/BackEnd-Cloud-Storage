package org.example.mvc.repository;

import org.example.mvc.domain.dto.UserChangePassword;
import org.example.mvc.domain.dto.UserFindIdDTO;
import org.example.mvc.domain.dto.UserFindPasswordDTO;
import org.example.mvc.domain.dto.UserLoginDTO;
import org.example.mvc.domain.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository {

    int checkDuplicateId(String userId);
    int register(User user);

    int isRegister(UserLoginDTO userLoginDTO);

    String findUserId(UserFindIdDTO userFindIdDTO);

    int findId(UserFindPasswordDTO userFindPasswordDTO);

    int changePassword(UserChangePassword userChangePassword);
}
