package com.codeosseum.ares.servermanagement.registration;

import com.codeosseum.ares.eventbus.dispatch.EventConsumer;
import com.codeosseum.ares.eventbus.dispatch.EventDispatcher;
import com.codeosseum.ares.servermanagement.registry.ServerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DeregistrationEventConsumer implements EventConsumer<DeregistrationEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeregistrationEventConsumer.class);

    private final ServerRegistry serverRegistry;

    public DeregistrationEventConsumer(final ServerRegistry serverRegistry) {
        this.serverRegistry = serverRegistry;
    }

    @Override
    public void accept(final DeregistrationEvent deregistrationEvent) {
        serverRegistry.findByIdentifier(deregistrationEvent.getIdentifier())
                .ifPresent(server -> {
                    LOGGER.info("Deregistering server: {}", server.getIdentifier());

                    serverRegistry.unregisterServer(server);
                });
    }
}
