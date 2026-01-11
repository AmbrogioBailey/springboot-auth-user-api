package com.ambrogio.springbootauthuserapi.dto;

import com.ambrogio.springbootauthuserapi.model.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashSet;
import java.util.Set;

public class UserResponse {

    private Long id;
    private String username;
    private String email;
    private Set<Role> roles;

    public UserResponse(Long id, String username, String email, Set<Role> roles) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
    public String getEmail() {
        return email;
    }
    public Set<Role> getRoles() {
        return roles;
    }
}
