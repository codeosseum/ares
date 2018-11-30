package com.codeosseum.ares.servermanagement.config;

import com.codeosseum.ares.eventbus.registry.EventRegistry;
import com.codeosseum.ares.servermanagement.registration.DeregistrationEvent;
import com.codeosseum.ares.servermanagement.registration.RegistrationEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class RegistrationConfig {
    @Autowired
    private EventRegistry eventRegistry;

    @PostConstruct
    public void registerEvents() {
        eventRegistry.registerEvent(RegistrationEvent.IDENTIFIER, RegistrationEvent.class);
        eventRegistry.registerEvent(DeregistrationEvent.IDENTIFIER, DeregistrationEvent.class);
    }
}
