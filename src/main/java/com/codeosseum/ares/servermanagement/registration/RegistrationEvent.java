package com.codeosseum.ares.servermanagement.registration;

import lombok.Value;

@Value
public final class RegistrationEvent {
    public static final String IDENTIFIER = "signup";

    private final String serverIdentifier;

    private final String uri;
}
