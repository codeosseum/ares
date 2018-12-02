package com.codeosseum.ares.matchmaking.foundation.matchmaker;

import lombok.Value;

@Value
public class MatchAssignedEvent {
    public static final String IDENTIFIER = "match-assigned";

    private final AssignedMatch match;
}
