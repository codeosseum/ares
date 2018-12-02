package com.codeosseum.ares.matchmaking.foundation.matchmaker;

import lombok.Value;

@Value
public class MatchMadeEvent {
    private final AssignedMatch match;
}
