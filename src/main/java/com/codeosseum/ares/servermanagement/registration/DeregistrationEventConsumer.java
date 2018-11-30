package com.codeosseum.ares.servermanagement.registration;

import com.codeosseum.ares.eventbus.dispatch.EventConsumer;
import com.codeosseum.ares.eventbus.dispatch.EventDispatcher;
import com.codeosseum.ares.servermanagement.registry.ServerRegistry;
import org.springframework.stereotype.Component;

@Component
public class DeregistrationEventConsumer implements EventConsumer<DeregistrationEvent> {
    private final ServerRegistry serverRegistry;

    public DeregistrationEventConsumer(final ServerRegistry serverRegistry) {
        this.serverRegistry = serverRegistry;
    }

    @Override
    public void accept(final DeregistrationEvent deregistrationEvent) {
        serverRegistry.findByIdentifier(deregistrationEvent.getIdentifier())
                .ifPresent(serverRegistry::unregisterServer);
    }
}
