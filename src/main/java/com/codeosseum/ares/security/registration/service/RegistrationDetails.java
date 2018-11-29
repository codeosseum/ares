package com.codeosseum.ares.security.registration.service;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public final class RegistrationDetails {
    private final String username;

    private final String password;

    private final String email;

    @Override
    public String toString() {
        return "RegistrationDetails{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
