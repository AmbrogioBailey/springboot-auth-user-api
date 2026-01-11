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
    public ResponseEntity<UserResponse> register(
            @Valid @RequestBody RegisterRequest request) {

        User savedUser = userService.registerUser(request);
        UserResponse response = new UserResponse(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getEmail(),
                savedUser.getRoles()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @GetMapping("/api/test/secure")
    public String secure() {
        return "SECURE";
    }

}
