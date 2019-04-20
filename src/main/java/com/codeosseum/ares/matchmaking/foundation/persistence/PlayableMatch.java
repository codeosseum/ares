package com.codeosseum.ares.matchmaking.foundation.persistence;

import com.codeosseum.ares.matchmaking.foundation.matchmaker.MatchConfiguration;
import com.codeosseum.ares.servermanagement.Server;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class PlayableMatch {
    private final String id;

    private final MatchConfiguration matchConfiguration;

    private final Server server;

    private final String joinPassword;
}
