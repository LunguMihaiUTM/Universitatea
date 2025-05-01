package com.universitatea.util;

import com.universitatea.entity.User;
import com.universitatea.exception.ResourceNotFoundException;
import com.universitatea.repository.UserRepository;
import com.universitatea.service.impl.JwtServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserExtractServiceImpl {

    private JwtServiceImpl jwtService;
    private UserRepository userRepository;

    public User getUser(String jwtToken) {
        String username = jwtService.extractUsername(jwtToken);
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("No user found"));
    }
}
