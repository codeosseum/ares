package com.codeosseum.ares.matchmaking.foundation.matchmaker;

import lombok.Value;

@Value
public class MatchMadeEvent {
    public static final String IDENTIFIER = "match-made";

    private final AssignedMatch match;
}
