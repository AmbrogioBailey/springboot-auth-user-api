package com.ambrogio.springbootauthuserapi.controller;

import com.ambrogio.springbootauthuserapi.dto.RegisterRequest;
import com.ambrogio.springbootauthuserapi.dto.UserResponse;
import com.ambrogio.springbootauthuserapi.model.User;
import com.ambrogio.springbootauthuserapi.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest request) {

        User savedUser = userService.registerUser(request);

        UserResponse response = UserResponse.builder()
                .id(savedUser.getId())
                .username(savedUser.getUsername())
                .email(savedUser.getEmail())
                .roles(savedUser.getRoles())
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Note: because the class already has /api/auth at the top,
    // this endpoint will become /api/auth/test/secure
    @GetMapping("/test/secure")
    public String secure() {
        return "SECURE";
    }
}

