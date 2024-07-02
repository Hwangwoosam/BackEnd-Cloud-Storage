package org.example.mvc.repository;

import org.example.mvc.domain.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository {

    int checkDuplicateId(String userId);
    int register(User user);

    User login(String id, String password);

}
