package com.codeosseum.ares.matchmaking.faultseeding.twoplayer;

import com.codeosseum.ares.matchmaking.foundation.matchmaker.Matchmaker;
import com.codeosseum.ares.matchmaking.foundation.matchmaker.MatchmakingService;
import com.codeosseum.ares.player.Player;
import com.codeosseum.ares.player.PlayerService;
import org.springframework.stereotype.Service;

@Service
public class TwoPlayerFaultSeedingMatchmakingService extends MatchmakingService<TwoPlayerFaultSeedingMatchmakingRequest, TwoPlayerFaultSeedingMatchmakingProfile, TwoPlayerFaultSeedingMatchConfiguration> {
    public TwoPlayerFaultSeedingMatchmakingService(Matchmaker<TwoPlayerFaultSeedingMatchmakingProfile, TwoPlayerFaultSeedingMatchConfiguration> matchmaker, PlayerService playerService) {
        super(matchmaker, playerService);
    }

    @Override
    protected TwoPlayerFaultSeedingMatchmakingProfile convertRequestToProfile(final TwoPlayerFaultSeedingMatchmakingRequest request, final Player player) {
        return new TwoPlayerFaultSeedingMatchmakingProfile(player.getUsername(), player.getRank());
    }
}
