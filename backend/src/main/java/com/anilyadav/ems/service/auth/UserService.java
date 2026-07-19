package com.anilyadav.ems.service.auth;

import com.anilyadav.ems.dto.request.UserRequest;
import com.anilyadav.ems.dto.response.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse createUser(UserRequest userRequest);

    UserResponse getUserById(Long id);

    List<UserResponse> getAllUsers();

    UserResponse updateUser(Long id, UserRequest userRequest);

    void deleteUser(Long id);
}