package com.codeosseum.ares.matchmaking.foundation.serverallocation;

import com.codeosseum.ares.eventbus.dispatch.EventDispatcher;
import com.codeosseum.ares.matchmaking.foundation.notificator.EventAwarePlayerNotificatorImpl;
import com.codeosseum.ares.matchmaking.foundation.persistence.MatchPersistedEvent;
import com.codeosseum.ares.servermanagement.Server;
import com.codeosseum.ares.servermanagement.registry.ServerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

public class EventAwareServerAllocatorImpl implements ServerAllocator {
    private static final Logger LOGGER = LoggerFactory.getLogger(EventAwarePlayerNotificatorImpl.class);

    private final ServerRegistry serverRegistry;

    private final Set<String> takenServers;

    public EventAwareServerAllocatorImpl(ServerRegistry serverRegistry, EventDispatcher eventDispatcher) {
        this.serverRegistry = serverRegistry;
        this.takenServers = new HashSet<>();

        eventDispatcher.registerConsumer(MatchPersistedEvent.class, this::consumeMatchPersistedEvent);
    }

    @Override
    public List<Server> findAllAvailableServers() {
        return serverRegistry.findAll().stream()
                .filter(this::serverIsFree)
                .collect(Collectors.toList());
    }

    @Override
    public void free(final String serverId) {
        takenServers.remove(requireNonNull(serverId));
    }

    private void consumeMatchPersistedEvent(final MatchPersistedEvent event) {
        LOGGER.info("Setting server {} as taken", event.getMatch().getServer().getIdentifier());

        takenServers.add(event.getMatch().getServer().getIdentifier());
    }

    private boolean serverIsFree(final Server server) {
        return !takenServers.contains(server.getIdentifier());
    }
}
