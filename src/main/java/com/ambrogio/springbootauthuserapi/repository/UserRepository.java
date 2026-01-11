package com.ambrogio.springbootauthuserapi.repository;

import com.ambrogio.springbootauthuserapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

     Optional<User> findByUsername(String username);

     Optional<User> findByEmail(String email);
}


