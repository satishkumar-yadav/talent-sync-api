package com.sky.service;

import com.sky.payload.AuthResponse;
import com.sky.payload.LoginRequest;
import com.sky.payload.SignupRequest;

public interface AuthService {
    
    AuthResponse signup(SignupRequest req) throws Exception; // throws UserAlreadyExistsException

    AuthResponse login(LoginRequest req);
}
 