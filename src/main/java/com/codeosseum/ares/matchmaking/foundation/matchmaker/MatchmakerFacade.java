package com.codeosseum.ares.matchmaking.foundation.matchmaker;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MatchmakerFacade {
    public Optional<AssignedMatch> getMatchForPlayer(final String username) {
        return Optional.empty();
    }

    public void removePlayerFromMatchmaking(final String username) {

    }
}
