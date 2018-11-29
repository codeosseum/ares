package com.codeosseum.ares.security.registration.controller;

import com.codeosseum.ares.security.registration.constraint.UniqueEmail;
import com.codeosseum.ares.security.registration.constraint.UniqueUsername;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public final class Registration {
    @NotNull
    @UniqueUsername
    private final String username;

    @NotNull
    @Size(min = 10)
    private final String password;

    @NotNull
    @Email
    @UniqueEmail
    private final String email;
}
