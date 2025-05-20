package com.universitatea.controller;

import com.universitatea.dto.LoginResponse;
import com.universitatea.dto.UserDTO;
import com.universitatea.entity.User;
import com.universitatea.service.AuthenticationService;
import com.universitatea.service.impl.JwtServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthenticationController {
    private final JwtServiceImpl jwtService;

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserDTO input) {
        return ResponseEntity.ok(authenticationService.signup(input));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody UserDTO input) {
        User authenticatedUser = authenticationService.authenticate(input);

        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("role", authenticatedUser.getRole().name());
        extraClaims.put("userId", authenticatedUser.getId());

        String token = jwtService.generateToken(extraClaims, authenticatedUser);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(token);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());
        return ResponseEntity.ok(loginResponse);
    }

}
