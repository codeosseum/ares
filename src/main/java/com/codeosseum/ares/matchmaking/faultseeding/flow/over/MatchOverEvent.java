package com.codeosseum.ares.matchmaking.faultseeding.flow.over;

import lombok.Value;

@Value
public class MatchOverEvent {
    public static final String IDENTIFIER = "fault-seeding-match-over";

    private final String matchId;

    private final String serverId;
}
