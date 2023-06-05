package com.steppedua.securityproject.service;

import com.steppedua.securityproject.domain.JwtRequest;
import com.steppedua.securityproject.domain.JwtResponse;

public interface AuthService {
    JwtResponse login(JwtRequest loginRequest);

    JwtResponse refresh(String refreshToken);

    JwtResponse getAccessToken(String refreshToken);
}
