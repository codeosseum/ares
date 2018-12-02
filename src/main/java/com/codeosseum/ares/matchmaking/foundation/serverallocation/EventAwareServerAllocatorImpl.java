package com.codeosseum.ares.matchmaking.foundation.serverallocation;

import com.codeosseum.ares.eventbus.dispatch.EventDispatcher;
import com.codeosseum.ares.matchmaking.foundation.persistence.MatchPersistedEvent;
import com.codeosseum.ares.servermanagement.Server;
import com.codeosseum.ares.servermanagement.registry.ServerRegistry;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class EventAwareServerAllocatorImpl implements ServerAllocator {
    private final ServerRegistry serverRegistry;

    private final Map<String, URI> takenServerMap;

    public EventAwareServerAllocatorImpl(ServerRegistry serverRegistry, EventDispatcher eventDispatcher) {
        this.serverRegistry = serverRegistry;
        this.takenServerMap = new ConcurrentHashMap<>();

        eventDispatcher.registerConsumer(this::consumeMatchPersistedEvent);
    }

    @Override
    public List<Server> findAllAvailableServers() {
        return serverRegistry.findAll().stream()
                .filter(server -> takenServerMap.containsValue(server.getUri()))
                .collect(Collectors.toList());
    }

    private void consumeMatchPersistedEvent(final MatchPersistedEvent event) {
        takenServerMap.put(event.getMatch().getId(), event.getMatch().getServer().getUri());
    }
}
