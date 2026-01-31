package com.ambrogio.springbootauthuserapi.service;

import com.ambrogio.springbootauthuserapi.dto.RegisterRequest;
import com.ambrogio.springbootauthuserapi.dto.UserResponse;
import com.ambrogio.springbootauthuserapi.exception.EmailAlreadyExistsException;
import com.ambrogio.springbootauthuserapi.exception.UsernameAlreadyExistsException;
import com.ambrogio.springbootauthuserapi.model.Role;
import com.ambrogio.springbootauthuserapi.model.User;
import com.ambrogio.springbootauthuserapi.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(u -> UserResponse.builder()
                        .id(u.getId())
                        .username(u.getUsername())
                        .email(u.getEmail())
                        .roles(u.getRoles())
                        .build())
                .toList();
    }

    public User registerUser(RegisterRequest request) {

        // Build user object
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());

        // Basic password checks (we can move these to validation annotations later)
        if (request.getPassword() == null || request.getPassword().isBlank()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
        if (request.getPassword().length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters");
        }

        // Uniqueness checks
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new UsernameAlreadyExistsException("Username already exists: " + user.getUsername());
        }
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("Email already exists: " + user.getEmail());
        }

        // Hash password + assign default role
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));

        user.getRoles().clear();
        user.getRoles().add(Role.USER);

        return userRepository.save(user);
    }
}



