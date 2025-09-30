package com.nick.ecommerce.user_service.controller;

import com.nick.ecommerce.user_service.dto.JwtResponse;
import com.nick.ecommerce.user_service.dto.LoginRequest;
import com.nick.ecommerce.user_service.dto.RegisterRequest;
import com.nick.ecommerce.user_service.model.Role;
import com.nick.ecommerce.user_service.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/register-user")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest request) {
        authService.register(request, Role.ERole.ROLE_USER);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/register-admin")
    public ResponseEntity<?> registerAdmin(@RequestBody RegisterRequest request) {
        authService.register(request, Role.ERole.ROLE_ADMIN);
        return ResponseEntity.ok("Admin registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}