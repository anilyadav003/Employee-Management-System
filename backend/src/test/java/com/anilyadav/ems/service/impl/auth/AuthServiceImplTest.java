package com.anilyadav.ems.service.impl.auth;

import com.anilyadav.ems.dto.request.LoginRequest;
import com.anilyadav.ems.dto.response.LoginResponse;
import com.anilyadav.ems.entity.auth.Role;
import com.anilyadav.ems.entity.auth.User;
import com.anilyadav.ems.enums.RoleType;
import com.anilyadav.ems.repository.UserRepository;
import com.anilyadav.ems.security.JwtService;
import com.anilyadav.ems.service.AuthServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthServiceImpl authService;

    @Test
    void login_ShouldThrowUsernameNotFoundException_WhenUserDoesNotExist() {

        // Arrange
        LoginRequest request = new LoginRequest();
        request.setUsername("anil");
        request.setPassword("password");

        when(authenticationManager.authenticate(any()))
                .thenReturn(null);

        when(userRepository.findByUsername("anil"))
                .thenReturn(Optional.empty());

        // Act & Assert
        UsernameNotFoundException exception = assertThrows(
                UsernameNotFoundException.class,
                () -> authService.login(request)
        );

        assertEquals("User not found", exception.getMessage());

        verify(authenticationManager).authenticate(any());
        verify(userRepository).findByUsername("anil");

        verify(jwtService, never()).generateToken(any());
    }
    @Test
    void login_ShouldThrowBadCredentialsException_WhenPasswordIsInvalid() {

        // Arrange
        LoginRequest request = new LoginRequest();
        request.setUsername("anil");
        request.setPassword("wrongPassword");

        when(authenticationManager.authenticate(any()))
                .thenThrow(new BadCredentialsException("Bad credentials"));

        // Act & Assert
        BadCredentialsException exception = assertThrows(
                BadCredentialsException.class,
                () -> authService.login(request)
        );

        assertEquals("Bad credentials", exception.getMessage());

        verify(authenticationManager).authenticate(any());

        verify(userRepository, never()).findByUsername(anyString());
        verify(jwtService, never()).generateToken(any());
    }
}