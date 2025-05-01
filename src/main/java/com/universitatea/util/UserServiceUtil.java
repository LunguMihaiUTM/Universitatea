package com.universitatea.util;

import com.universitatea.dto.UserDTO;
import com.universitatea.entity.User;
import org.springframework.stereotype.Service;

@Service
public class UserServiceUtil {
    public User convertDTOToUser(UserDTO userDTO) {
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setRole(userDTO.getRole());
        return user;
    }
}
