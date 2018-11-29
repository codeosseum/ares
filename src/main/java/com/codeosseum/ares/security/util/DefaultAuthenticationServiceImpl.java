package com.codeosseum.ares.security.util;

import com.codeosseum.ares.security.login.UserDetails;
import com.codeosseum.ares.user.Role;
import com.codeosseum.ares.user.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DefaultAuthenticationServiceImpl implements AuthenticationService {
    @Override
    public boolean hasAuthenticatedUser() {
        return getAuthenticatedUser().isPresent();
    }

    @Override
    public boolean hasRole(final Role role) {
        return this.getAuthenticatedUser()
                .filter(user -> user.getRoles().contains(role))
                .isPresent();
    }

    @Override
    public Optional<User> getAuthenticatedUser() {
         return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                 .map(obj -> (UserDetails)obj)
                 .map(UserDetails::getUser);
    }
}
