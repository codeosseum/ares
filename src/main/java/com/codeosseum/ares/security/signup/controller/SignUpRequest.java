package com.codeosseum.ares.security.signup.controller;

import com.codeosseum.ares.security.signup.constraint.UniqueEmail;
import com.codeosseum.ares.security.signup.constraint.UniqueUsername;
import lombok.Value;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Value
public final class SignUpRequest {
    @NotNull
    @NotEmpty(message = "{username.notempty}")
    @UniqueUsername
    private final String username;

    @NotNull
    @Size(min = 10, message = "{password.size}")
    private final String password;

    @NotNull
    @NotEmpty(message = "{email.notempty}")
    @Email(message = "{email.valid}")
    @UniqueEmail
    private final String email;
}
