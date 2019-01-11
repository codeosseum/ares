package com.codeosseum.ares.servermanagement.registration;

import lombok.Value;

@Value
public final class RegistrationEvent {
    public static final String IDENTIFIER = "registration";

    private final String serverIdentifier;

    private final String uri;
}
