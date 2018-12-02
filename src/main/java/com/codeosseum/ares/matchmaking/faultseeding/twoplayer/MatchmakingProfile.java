package com.codeosseum.ares.matchmaking.faultseeding.twoplayer;

public class MatchmakingProfile extends com.codeosseum.ares.matchmaking.foundation.matchmaker.MatchmakingProfile {
    private final int rank;

    public MatchmakingProfile(String username, int rank) {
        super(username);
        this.rank = rank;
    }

    public int getRank() {
        return rank;
    }
}
