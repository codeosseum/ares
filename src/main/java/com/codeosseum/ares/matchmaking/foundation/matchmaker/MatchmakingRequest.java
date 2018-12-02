package com.codeosseum.ares.matchmaking.foundation.matchmaker;

public class MatchmakingRequest {
    private final String username;

    public MatchmakingRequest(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
