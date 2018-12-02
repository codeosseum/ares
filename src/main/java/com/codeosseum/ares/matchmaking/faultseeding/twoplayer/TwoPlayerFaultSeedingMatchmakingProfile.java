package com.codeosseum.ares.matchmaking.faultseeding.twoplayer;

import com.codeosseum.ares.matchmaking.foundation.matchmaker.MatchmakingProfile;

public class TwoPlayerFaultSeedingMatchmakingProfile extends MatchmakingProfile {
    private final int rank;

    public TwoPlayerFaultSeedingMatchmakingProfile(String username, int rank) {
        super(username);
        this.rank = rank;
    }

    public int getRank() {
        return rank;
    }
}
