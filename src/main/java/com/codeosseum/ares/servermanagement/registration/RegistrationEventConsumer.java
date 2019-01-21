package com.codeosseum.ares.servermanagement.registration;

import com.codeosseum.ares.eventbus.dispatch.EventConsumer;
import com.codeosseum.ares.servermanagement.Server;
import com.codeosseum.ares.servermanagement.registry.ServerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.net.URI;

@Component
public class RegistrationEventConsumer implements EventConsumer<RegistrationEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationEventConsumer.class);

    private final ServerRegistry serverRegistry;

    public RegistrationEventConsumer(final ServerRegistry serverRegistry) {
        this.serverRegistry = serverRegistry;
    }

    @Override
    public void accept(final RegistrationEvent registrationEvent) {
        final Server server = Server.builder()
                .identifier(registrationEvent.getServerIdentifier())
                .uri(registrationEvent.getUri())
                .build();

        LOGGER.info("Registering server {} - {}", server.getIdentifier(), server.getUri());

        serverRegistry.registerServer(server);
    }
}
