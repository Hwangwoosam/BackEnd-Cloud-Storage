package org.example.mvc.repository;

import org.example.mvc.domain.dto.UploadUserDTO;
import org.example.mvc.domain.dto.UserInfoDTO;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository {
    UserInfoDTO getUser(String userName);

    void setUser(UploadUserDTO userDTO);
}
