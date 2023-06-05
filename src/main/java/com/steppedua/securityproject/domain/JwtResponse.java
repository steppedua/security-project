package com.steppedua.securityproject.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JwtResponse {

    private final String TYPE = "Bearer";
    private String accessToken;
    private String refreshToken;

}
