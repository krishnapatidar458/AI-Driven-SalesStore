package com.SalesStore.service;

import com.SalesStore.dto.SignupRequest;
import com.SalesStore.request.LoginRequest;
import com.SalesStore.response.AuthResponse;

public interface AuthService {
    void signup(SignupRequest signupRequestequest);
    AuthResponse login(LoginRequest loginRequest);
}
