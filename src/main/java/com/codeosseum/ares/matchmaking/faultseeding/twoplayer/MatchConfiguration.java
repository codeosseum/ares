package com.codeosseum.ares.matchmaking.faultseeding.twoplayer;

import com.codeosseum.ares.match.Mode;

import java.util.List;

public class MatchConfiguration extends com.codeosseum.ares.matchmaking.foundation.matchmaker.MatchConfiguration {
    public MatchConfiguration(List<String> players) {
        super(Mode.TWO_PLAYER_FAULT_SEEDING, players);
    }
}
