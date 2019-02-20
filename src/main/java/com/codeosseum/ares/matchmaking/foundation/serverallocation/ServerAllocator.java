package com.codeosseum.ares.matchmaking.foundation.serverallocation;

import com.codeosseum.ares.servermanagement.Server;

import java.util.List;

public interface ServerAllocator {
    List<Server> findAllAvailableServers();

    void free(String serverId);
}
