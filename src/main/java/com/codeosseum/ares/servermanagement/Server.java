package com.codeosseum.ares.servermanagement;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Server {
    private final String identifier;

    private final String uri;
}
