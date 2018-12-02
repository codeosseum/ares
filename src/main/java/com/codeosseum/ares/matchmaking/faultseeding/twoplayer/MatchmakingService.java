package com.codeosseum.ares.matchmaking.faultseeding.twoplayer;

import com.codeosseum.ares.matchmaking.foundation.matchmaker.Matchmaker;
import com.codeosseum.ares.player.Player;
import com.codeosseum.ares.player.PlayerService;

public class MatchmakingService extends com.codeosseum.ares.matchmaking.foundation.matchmaker.MatchmakingService<
            MatchmakingRequest, MatchmakingProfile, MatchConfiguration> {
    public MatchmakingService(Matchmaker<MatchmakingProfile, MatchConfiguration> matchmaker, PlayerService playerService) {
        super(matchmaker, playerService);
    }

    @Override
    protected MatchmakingProfile convertRequestToProfile(final MatchmakingRequest request, final Player player) {
        return new MatchmakingProfile(player.getUsername(), player.getRank());
    }
}
