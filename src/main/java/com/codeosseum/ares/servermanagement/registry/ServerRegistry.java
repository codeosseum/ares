package com.codeosseum.ares.servermanagement.registry;

import com.codeosseum.ares.servermanagement.Server;

import java.util.Collection;
import java.util.Optional;

public interface ServerRegistry {
    void registerServer(Server server);

    void unregisterServer(Server server);

    Optional<Server> findByIdentifier(String identifier);

    Collection<Server> findAll();
}
