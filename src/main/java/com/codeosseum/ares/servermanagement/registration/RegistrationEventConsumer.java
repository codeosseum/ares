package com.codeosseum.ares.servermanagement.registration;

import com.codeosseum.ares.eventbus.dispatch.EventConsumer;
import com.codeosseum.ares.servermanagement.Server;
import com.codeosseum.ares.servermanagement.registry.ServerRegistry;
import org.springframework.stereotype.Component;

@Component
public class RegistrationEventConsumer implements EventConsumer<RegistrationEvent> {
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

        serverRegistry.registerServer(server);
    }
}
