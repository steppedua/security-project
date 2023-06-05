package com.steppedua.securityproject.service;

import com.steppedua.securityproject.domain.User;

public interface UserService {
    User getByUsername(String username);
}
