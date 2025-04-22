package com.universitatea.service.impl;

import com.universitatea.dto.UserDTO;
import com.universitatea.entity.User;
import com.universitatea.enums.Role;
import com.universitatea.repository.UserRepository;
import com.universitatea.service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void signup(UserDTO input) {
        var user = userRepository.findByEmail(input.getEmail());
        if (user.isPresent()) {
            throw new RuntimeException("Username is already in use");
        }
        User newUser = new User();
        if(input.getRole() == null)
            newUser.setRole(Role.STUDENT);
        else newUser.setRole(input.getRole());

            newUser.setPassword(passwordEncoder.encode(input.getPassword()));
            newUser.setEmail(input.getEmail());
            userRepository.save(newUser);

    }

    public User authenticate(UserDTO input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        return userRepository.findByEmail(input.getEmail())
                .orElseThrow();
    }
}