package com.codeosseum.ares.security.signup.service;

import java.util.Objects;

public class RegistrationFailedException extends Exception {
    private final RegistrationDetails registrationDetails;

    public RegistrationFailedException(final RegistrationDetails registrationDetails) {
        this.registrationDetails = Objects.requireNonNull(registrationDetails);
    }

    public RegistrationFailedException(final RegistrationDetails registrationDetails, Throwable cause) {
        super(cause);

        this.registrationDetails = Objects.requireNonNull(registrationDetails);
    }

    public RegistrationDetails getRegistrationDetails() {
        return registrationDetails;
    }

    @Override
    public String getMessage() {
        return "Failed to register new user " + registrationDetails;
    }
}
