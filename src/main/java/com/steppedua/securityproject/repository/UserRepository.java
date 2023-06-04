package com.steppedua.securityproject.repository;

import com.steppedua.securityproject.domain.Role;
import com.steppedua.securityproject.domain.User;
import lombok.NonNull;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UserRepository {
    private final List<User> users;

    public UserRepository() {
        this.users = List.of(
                new User("anton", "1234", "Антон", "Иванов", Collections.singleton(Role.ROLE_USER)),
                new User("ivan", "12345", "Сергей", "Петров", Collections.singleton(Role.ROLE_ADMIN))
        );
    }

    public Optional<User> getByLogin(@NonNull String login) {
        return users.stream()
                .filter(user -> login.equals(user.getLogin()))
                .findFirst();
    }
}
