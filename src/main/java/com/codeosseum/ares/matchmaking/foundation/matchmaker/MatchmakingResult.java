package com.codeosseum.ares.matchmaking.foundation.matchmaker;

import com.codeosseum.ares.match.Mode;

import java.time.LocalDateTime;
import java.util.List;

public class MatchmakingResult {
    private final Mode mode;

    private final List<String> players;

    private final LocalDateTime createdAt;

    public MatchmakingResult(Mode mode, List<String> players, LocalDateTime createdAt) {
        this.mode = mode;
        this.players = players;
        this.createdAt = createdAt;
    }

    public Mode getMode() {
        return mode;
    }

    public List<String> getPlayers() {
        return players;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
