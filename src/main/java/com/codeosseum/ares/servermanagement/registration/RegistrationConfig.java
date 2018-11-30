package com.codeosseum.ares.servermanagement.registration;

import com.codeosseum.ares.eventbus.dispatch.EventDispatcher;
import com.codeosseum.ares.eventbus.registry.EventRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class RegistrationConfig {
    @Autowired
    private EventRegistry eventRegistry;

    @Autowired
    private EventDispatcher eventDispatcher;

    @Autowired
    private RegistrationEventConsumer registrationEventConsumer;

    @Autowired
    private DeregistrationEventConsumer deregistrationEventConsumer;

    @PostConstruct
    public void registerEvents() {
        eventRegistry.registerEvent(RegistrationEvent.IDENTIFIER, RegistrationEvent.class);
        eventRegistry.registerEvent(DeregistrationEvent.IDENTIFIER, DeregistrationEvent.class);
    }

    @PostConstruct
    public void registerConsumers() {
        eventDispatcher.registerConsumer(registrationEventConsumer);
        eventDispatcher.registerConsumer(deregistrationEventConsumer);
    }
}
