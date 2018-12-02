package com.codeosseum.ares.matchmaking.foundation.matchmaker;

import com.codeosseum.ares.match.Mode;

import java.util.List;

public class MatchConfiguration {
    private final Mode mode;

    private final List<String> players;

    public MatchConfiguration(Mode mode, List<String> players) {
        this.mode = mode;
        this.players = players;
    }

    public Mode getMode() {
        return mode;
    }

    public List<String> getPlayers() {
        return players;
    }
}
