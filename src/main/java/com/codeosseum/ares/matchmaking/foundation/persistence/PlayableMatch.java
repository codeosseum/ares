package com.codeosseum.ares.matchmaking.foundation.persistence;

import com.codeosseum.ares.matchmaking.foundation.matchmaker.MatchConfiguration;
import com.codeosseum.ares.servermanagement.Server;
import lombok.Builder;

@Builder
public class PlayableMatch {
    private final String id;

    private final MatchConfiguration matchConfiguration;

    private final Server server;

    public String getId() {
        return id;
    }

    public MatchConfiguration getMatchConfiguration() {
        return matchConfiguration;
    }

    public Server getServer() {
        return server;
    }
}
