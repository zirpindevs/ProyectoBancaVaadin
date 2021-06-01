package com.example.application.backend.security.service;

import com.example.application.backend.payload.request.LoginRequest;
import com.example.application.backend.payload.response.JwtResponse;

public interface AuthService {

    public JwtResponse authenticateUser(LoginRequest loginRequest);
}
