package com.codeosseum.ares.matchmaking.foundation.serverallocation;

import java.util.Optional;

public interface ServerAllocator {
    void free(String serverId);

    Optional<String> allocate();
}
