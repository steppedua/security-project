package com.steppedua.securityproject.service;

import com.steppedua.securityproject.domain.JwtRequest;
import com.steppedua.securityproject.domain.JwtResponse;
import com.steppedua.securityproject.exception.AuthenticationException;
import com.steppedua.securityproject.security.JwtTokenProvider;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public JwtResponse login(@NonNull JwtRequest authRequest) {
        final var user = userService.getByUsername(authRequest.getUsername());
        if (!user.getPassword().equals(authRequest.getPassword())) {
            throw new AuthenticationException("Invalid password for username:" + user.getUsername());
        }

        final var accessToken = jwtTokenProvider.generateAccessToken(user);
        final var refreshToken = jwtTokenProvider.generateRefreshToken(user);

        return new JwtResponse(accessToken, refreshToken);
    }

    @Override
    public JwtResponse refresh(@NonNull String refreshToken) {
        if (!jwtTokenProvider.validateRefreshToken(refreshToken)) {
            throw new AuthenticationException("Invalid JWT token");
        }

        final var claims = jwtTokenProvider.getRefreshClaims(refreshToken);
        final var username = claims.getSubject();

        final var user = userService.getByUsername(username);
        final var accessToken = jwtTokenProvider.generateAccessToken(user);
        final var newRefreshToken = jwtTokenProvider.generateRefreshToken(user);

        return new JwtResponse(accessToken, newRefreshToken);
    }

    @Override
    public JwtResponse getAccessToken(@NonNull String refreshToken) {
        if (!jwtTokenProvider.validateRefreshToken(refreshToken)) {
            return new JwtResponse(null, null);
        }

        final var claims = jwtTokenProvider.getRefreshClaims(refreshToken);
        final var username = claims.getSubject();
        final var user = userService.getByUsername(username);
        final var accessToken = jwtTokenProvider.generateAccessToken(user);

        return new JwtResponse(accessToken, null);
    }
}
