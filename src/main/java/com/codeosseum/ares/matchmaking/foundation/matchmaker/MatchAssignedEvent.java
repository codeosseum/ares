package com.codeosseum.ares.matchmaking.foundation.matchmaker;

import com.codeosseum.ares.servermanagement.Server;
import lombok.Value;

@Value
public class MatchAssignedEvent {
    public static final String IDENTIFIER = "match-assigned";

    private final MatchConfiguration matchConfiguration;

    private final Server assignedServer;
}
