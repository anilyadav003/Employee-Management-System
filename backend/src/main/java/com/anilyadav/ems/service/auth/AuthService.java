package com.anilyadav.ems.service.auth;

import com.anilyadav.ems.dto.request.LoginRequest;
import com.anilyadav.ems.dto.response.LoginResponse;

public interface AuthService {

    LoginResponse login(LoginRequest request);
}