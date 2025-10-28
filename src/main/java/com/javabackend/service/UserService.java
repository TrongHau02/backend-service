package com.javabackend.service;

import com.javabackend.controller.request.UserCreateRequest;
import com.javabackend.controller.request.UserPasswordRequest;
import com.javabackend.controller.request.UserUpdateRequest;
import com.javabackend.controller.response.UserResponse;

import java.util.List;

public interface UserService {
    List<UserResponse> findAll();

    UserResponse findById(Long id);

    UserResponse findByUsername(String username);

    UserResponse findByEmail(String email);

    long save(UserCreateRequest req);

    void update(UserUpdateRequest req);

    void changePassword(UserPasswordRequest req);

    void delete(Long id);
}
