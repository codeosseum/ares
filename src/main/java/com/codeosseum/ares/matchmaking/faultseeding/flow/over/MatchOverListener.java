package com.codeosseum.ares.matchmaking.faultseeding.flow.over;

import com.codeosseum.ares.eventbus.dispatch.EventConsumer;
import com.codeosseum.ares.matchmaking.foundation.serverallocation.ServerAllocator;
import org.springframework.stereotype.Component;

@Component
public class MatchOverListener implements EventConsumer<MatchOverEvent> {
    private final ServerAllocator serverAllocator;

    public MatchOverListener(final ServerAllocator serverAllocator) {
        this.serverAllocator = serverAllocator;
    }

    @Override
    public void accept(final MatchOverEvent event) {
        serverAllocator.free(event.getServerId());
    }
}
