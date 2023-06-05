package com.steppedua.securityproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/greeting")
@RequiredArgsConstructor
public class GreetingController {
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping("/user")
    public String getResponseGreetingUser() {
        return "Hello, user!";
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/admin")
    public String getResponseGreetingAdmin() {
        return "Hello, admin!";
    }
}
