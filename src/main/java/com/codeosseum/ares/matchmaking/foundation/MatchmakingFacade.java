package com.codeosseum.ares.matchmaking.foundation;

import com.codeosseum.ares.matchmaking.foundation.matchmaker.AssignedMatch;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MatchmakingFacade {
    public Optional<AssignedMatch> getMatchForPlayer(final String username) {
        return Optional.empty();
    }

    public void removePlayerFromMatchmaking(final String username) {

    }
}
