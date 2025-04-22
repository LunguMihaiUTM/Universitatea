package com.universitatea.service;

import com.universitatea.dto.UserDTO;
import com.universitatea.entity.User;

public interface AuthenticationService {
    void signup(UserDTO input);

    User authenticate(UserDTO input);
}