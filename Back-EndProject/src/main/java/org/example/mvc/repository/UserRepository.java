package org.example.mvc.repository;

import org.example.mvc.domain.dto.UserLoginDTO;
import org.example.mvc.domain.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository {

    int checkDuplicateId(String userId);
    int register(User user);

    int isRegister(UserLoginDTO userLoginDTO);

}
