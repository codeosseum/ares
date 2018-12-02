package com.codeosseum.ares.matchmaking.faultseeding.twoplayer;

import com.codeosseum.ares.match.Mode;
import com.codeosseum.ares.matchmaking.foundation.matchmaker.MatchConfiguration;

import java.util.List;

public class TwoPlayerFaultSeedingMatchConfiguration extends MatchConfiguration {
    public TwoPlayerFaultSeedingMatchConfiguration(List<String> players) {
        super(Mode.TWO_PLAYER_FAULT_SEEDING, players);
    }
}
