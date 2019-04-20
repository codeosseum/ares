package com.codeosseum.ares.matchmaking.foundation.serverallocation;

import com.codeosseum.ares.servermanagement.Server;
import com.codeosseum.ares.servermanagement.registry.ServerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import static java.util.Objects.requireNonNull;

public class DefaultServerAllocatorImpl implements ServerAllocator {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultServerAllocatorImpl.class);

    private final ServerRegistry serverRegistry;

    private final Set<String> takenServers;

    public DefaultServerAllocatorImpl(final ServerRegistry serverRegistry) {
        this.serverRegistry = serverRegistry;
        this.takenServers = new CopyOnWriteArraySet<>();
    }

    @Override
    public void free(final String serverId) {
        takenServers.remove(requireNonNull(serverId));
    }

    @Override
    public Optional<String> allocate() {
        return serverRegistry.findAll().stream()
                .filter(this::serverIsFree)
                .map(Server::getIdentifier)
                .findFirst();
    }

    private boolean serverIsFree(final Server server) {
        return !takenServers.contains(server.getIdentifier());
    }
}
