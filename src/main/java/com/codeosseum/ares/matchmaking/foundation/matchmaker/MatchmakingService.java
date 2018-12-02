package com.codeosseum.ares.matchmaking.foundation.matchmaker;

import com.codeosseum.ares.player.Player;
import com.codeosseum.ares.player.PlayerService;

public abstract class MatchmakingService<R extends MatchmakingRequest, P extends MatchmakingProfile, M extends MatchConfiguration> {
    private final Matchmaker<P, M> matchmaker;

    private final PlayerService playerService;

    public MatchmakingService(Matchmaker<P, M> matchmaker, PlayerService playerService) {
        this.matchmaker = matchmaker;
        this.playerService = playerService;
    }

    public final void addToMatchmaking(final R request) {
        playerService.findPlayerByUsername(request.getUsername())
                .map(player -> convertRequestToProfile(request, player))
                .ifPresent(matchmaker::addProfileToMatchmaking);
    }

    protected abstract P convertRequestToProfile(R request, Player player);
}
