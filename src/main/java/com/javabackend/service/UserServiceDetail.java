package com.javabackend.service;

import com.javabackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public record UserServiceDetail(UserRepository userRepository) {

    public UserDetailsService UserServiceDetail() {
        return userRepository::findByUsername;
    }
}
