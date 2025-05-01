package com.universitatea.service;

import com.universitatea.dto.UserDTO;
import com.universitatea.entity.User;

public interface AuthenticationService {
    String signup(UserDTO input);
    User authenticate(UserDTO input);
}