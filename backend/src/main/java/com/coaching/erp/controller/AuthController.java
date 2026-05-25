package com.coaching.erp.controller;

import com.coaching.erp.dto.LoginRequest;
import com.coaching.erp.entity.User;
import com.coaching.erp.repository.UserRepository;
import com.coaching.erp.security.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

@PostMapping("/login")
public String login(@RequestBody LoginRequest request) {

    User user = userRepository.findByUsername(request.getUsername())
            .orElseThrow(() -> new RuntimeException("User not found"));

    if (!user.getPassword().equals(request.getPassword())) {
        throw new RuntimeException("Invalid password");
    }

    return JwtUtil.generateToken(user.getUsername(), user.getRole().name());
}
}