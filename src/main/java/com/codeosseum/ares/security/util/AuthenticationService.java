package com.codeosseum.ares.security.util;

import com.codeosseum.ares.user.Role;
import com.codeosseum.ares.user.User;

import java.util.Optional;

public interface AuthenticationService {
    boolean hasAuthenticatedUser();

    boolean hasRole(Role role);

    Optional<User> getAuthenticatedUser();
}
