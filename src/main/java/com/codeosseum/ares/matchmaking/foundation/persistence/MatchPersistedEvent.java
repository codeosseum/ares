package com.codeosseum.ares.matchmaking.foundation.persistence;

import lombok.Value;

@Value
public class MatchPersistedEvent {
    public static final String IDENTIFIER = "match-persisted";

    private final PlayableMatch match;
}
