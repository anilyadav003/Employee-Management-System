package com.anilyadav.ems.service.impl.auth;

import com.anilyadav.ems.dto.request.UserRequest;
import com.anilyadav.ems.dto.response.UserResponse;
import com.anilyadav.ems.entity.auth.Role;
import com.anilyadav.ems.entity.auth.User;
import com.anilyadav.ems.exception.DuplicateResourceException;
import com.anilyadav.ems.exception.ResourceNotFoundException;
import com.anilyadav.ems.repository.RoleRepository;
import com.anilyadav.ems.repository.UserRepository;
import com.anilyadav.ems.service.auth.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public UserResponse createUser(UserRequest userRequest) {

        if (userRepository.existsByUsername(userRequest.getUsername())) {
            throw new DuplicateResourceException(
                    "Username already exists: " + userRequest.getUsername());
        }

        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new DuplicateResourceException(
                    "Email already exists: " + userRequest.getEmail());
        }

        Role role = roleRepository.findByName(userRequest.getRole())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Role not found: " + userRequest.getRole()));

        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());
        user.setEnabled(true);
        user.setRole(role);

        User savedUser = userRepository.save(user);

        return UserResponse.builder()
                .id(savedUser.getId())
                .username(savedUser.getUsername())
                .email(savedUser.getEmail())
                .enabled(savedUser.getEnabled())
                .role(savedUser.getRole().getName())
                .build();
    }

    @Override
    public UserResponse getUserById(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with id: " + id));

        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .enabled(user.getEnabled())
                .role(user.getRole().getName())
                .build();
    }

    @Override
    public List<UserResponse> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(user -> UserResponse.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .email(user.getEmail())
                        .enabled(user.getEnabled())
                        .role(user.getRole().getName())
                        .build())
                .toList();
    }

    @Override
    public UserResponse updateUser(Long id, UserRequest userRequest) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with id: " + id));

        if (!user.getUsername().equals(userRequest.getUsername())
                && userRepository.existsByUsername(userRequest.getUsername())) {

            throw new DuplicateResourceException(
                    "Username already exists: " + userRequest.getUsername());
        }

        if (!user.getEmail().equals(userRequest.getEmail())
                && userRepository.existsByEmail(userRequest.getEmail())) {

            throw new DuplicateResourceException(
                    "Email already exists: " + userRequest.getEmail());
        }

        Role role = roleRepository.findByName(userRequest.getRole())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Role not found: " + userRequest.getRole()));

        user.setUsername(userRequest.getUsername());
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());
        user.setRole(role);

        User updatedUser = userRepository.save(user);

        return UserResponse.builder()
                .id(updatedUser.getId())
                .username(updatedUser.getUsername())
                .email(updatedUser.getEmail())
                .enabled(updatedUser.getEnabled())
                .role(updatedUser.getRole().getName())
                .build();
    }

    @Override
    public void deleteUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with id: " + id));

        userRepository.delete(user);
    }
}