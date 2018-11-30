package com.codeosseum.ares.servermanagement.registration;

import lombok.Value;

@Value
public final class DeregistrationEvent {
    public static final String IDENTIFIER = "deregistration";

    private final String identifier;
}
