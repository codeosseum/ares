package com.codeosseum.ares.servermanagement;

import lombok.Builder;
import lombok.Value;

import java.net.URI;

@Value
@Builder
public class Server {
    private final String identifier;

    private final URI uri;
}
