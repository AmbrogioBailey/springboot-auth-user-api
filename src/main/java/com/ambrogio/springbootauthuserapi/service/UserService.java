package com.ambrogio.springbootauthuserapi.service;

import com.ambrogio.springbootauthuserapi.dto.RegisterRequest;
import com.ambrogio.springbootauthuserapi.dto.UserResponse;
import com.ambrogio.springbootauthuserapi.exception.EmailAlreadyExistsException;
import com.ambrogio.springbootauthuserapi.exception.UsernameAlreadyExistsException;
import com.ambrogio.springbootauthuserapi.model.Role;
import com.ambrogio.springbootauthuserapi.model.User;
import com.ambrogio.springbootauthuserapi.repository.UserRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

// @Service tells spring this is a business component
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(u -> new UserResponse(
                        u.getId(),
                        u.getUsername(),
                        u.getEmail(),
                        u.getRoles()
                ))
                .toList();
    }
    public User registerUser(RegisterRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());

        if (request.getPassword() == null || request.getPassword().isBlank()) {
            throw new IllegalArgumentException("Password hash cannot be empty");
        }
        if  (request.getPassword().length() < 8) {
            throw new IllegalArgumentException("Password length cannot be less than 8 characters");
        }
        if  (userRepository.findByUsername(user.getUsername()).isPresent()) {
           throw new UsernameAlreadyExistsException("Username already exists: " + user.getUsername());
       }
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
           throw new EmailAlreadyExistsException("Email already exists: " + user.getEmail());
       }


        user.setPasswordHash(
                passwordEncoder.encode(request.getPassword())
        );
        user.getRoles().clear();

       user.getRoles().add(Role.USER);

       return userRepository.save(user);
    }

}
