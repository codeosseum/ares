package com.codeosseum.ares.matchmaking.foundation.serverallocation;

import com.codeosseum.ares.eventbus.dispatch.EventDispatcher;
import com.codeosseum.ares.matchmaking.foundation.notificator.EventAwarePlayerNotificatorImpl;
import com.codeosseum.ares.matchmaking.foundation.persistence.MatchPersistedEvent;
import com.codeosseum.ares.servermanagement.Server;
import com.codeosseum.ares.servermanagement.registry.ServerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class EventAwareServerAllocatorImpl implements ServerAllocator {
    private static final Logger LOGGER = LoggerFactory.getLogger(EventAwarePlayerNotificatorImpl.class);

    private final ServerRegistry serverRegistry;

    private final Map<String, String> takenServerMap;

    public EventAwareServerAllocatorImpl(ServerRegistry serverRegistry, EventDispatcher eventDispatcher) {
        this.serverRegistry = serverRegistry;
        this.takenServerMap = new ConcurrentHashMap<>();

        eventDispatcher.registerConsumer(MatchPersistedEvent.class, this::consumeMatchPersistedEvent);
    }

    @Override
    public List<Server> findAllAvailableServers() {
        return serverRegistry.findAll().stream()
                .filter(server -> takenServerMap.containsValue(server.getUri()))
                .collect(Collectors.toList());
    }

    private void consumeMatchPersistedEvent(final MatchPersistedEvent event) {
        LOGGER.info("Setting server {} as taken for match with ID {}", event.getMatch().getServer().getIdentifier(), event.getMatch().getId());

        takenServerMap.put(event.getMatch().getId(), event.getMatch().getServer().getUri());
    }
}
