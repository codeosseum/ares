package com.codeosseum.ares.matchmaking.foundation.config;

import com.codeosseum.ares.eventbus.dispatch.EventDispatcher;
import com.codeosseum.ares.eventbus.registry.EventRegistry;
import com.codeosseum.ares.matchmaking.foundation.matchmaker.MatchAssignedEvent;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class EventConfig {
    private final EventDispatcher eventDispatcher;

    private final EventRegistry eventRegistry;

    public EventConfig(EventDispatcher eventDispatcher, EventRegistry eventRegistry) {
        this.eventDispatcher = eventDispatcher;
        this.eventRegistry = eventRegistry;
    }

    @PostConstruct
    public void registerEvents() {
        eventRegistry.registerEvent(MatchAssignedEvent.IDENTIFIER, MatchAssignedEvent.class);
    }
}
