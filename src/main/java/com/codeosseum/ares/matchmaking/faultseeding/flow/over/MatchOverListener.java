package com.codeosseum.ares.matchmaking.faultseeding.flow.over;

import java.util.List;

import com.codeosseum.ares.eventbus.dispatch.EventConsumer;
import com.codeosseum.ares.match.Match;
import com.codeosseum.ares.match.repository.MatchRepository;
import com.codeosseum.ares.matchmaking.foundation.notificator.PlayerNotificator;
import com.codeosseum.ares.matchmaking.foundation.serverallocation.ServerAllocator;
import org.springframework.stereotype.Component;

@Component
public class MatchOverListener implements EventConsumer<MatchOverEvent> {
    private final MatchRepository matchRepository;

    private final PlayerNotificator playerNotificator;

    private final ServerAllocator serverAllocator;

    public MatchOverListener(final MatchRepository matchRepository, final PlayerNotificator playerNotificator, final ServerAllocator serverAllocator) {
        this.matchRepository = matchRepository;
        this.playerNotificator = playerNotificator;
        this.serverAllocator = serverAllocator;
    }

    @Override
    public void accept(final MatchOverEvent event) {
        List<Match> matches = matchRepository.findAll();

        matchRepository.findById(event.getMatchId())
                .map(Match::getPlayers)
                .ifPresent(players -> players.forEach(playerNotificator::unsetMatchForPlayer));

        serverAllocator.free(event.getServerId());
    }
}
