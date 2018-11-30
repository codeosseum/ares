package com.codeosseum.ares.servermanagement.registry;

import com.codeosseum.ares.servermanagement.Server;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class DefaultServerRegistryImpl implements ServerRegistry {
    private final Map<String, Server> serverMap;

    public DefaultServerRegistryImpl() {
        this.serverMap = new ConcurrentHashMap<>();
    }

    @Override
    public void registerServer(final Server server) {
        serverMap.put(Objects.requireNonNull(server).getIdentifier(), server);
    }

    @Override
    public void unregisterServer(final Server server) {
        serverMap.remove(Objects.requireNonNull(server).getIdentifier());
    }

    @Override
    public Optional<Server> findByIdentifier(final String identifier) {
        return Optional.ofNullable(serverMap.get(Objects.requireNonNull(identifier)));
    }

    @Override
    public Collection<Server> findAll() {
        return Collections.unmodifiableCollection(serverMap.values());
    }
}
