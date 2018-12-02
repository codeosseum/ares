package com.codeosseum.ares.matchmaking.foundation;

import com.codeosseum.ares.matchmaking.foundation.matchmaker.AssignedMatch;
import com.codeosseum.ares.matchmaking.foundation.matchmaker.Matchmaker;
import com.codeosseum.ares.matchmaking.foundation.matchmaker.MatchmakingProfile;
import com.codeosseum.ares.matchmaking.foundation.matchmaker.MatchConfiguration;
import com.codeosseum.ares.matchmaking.foundation.notificator.PlayerNotificator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class MatchmakingFacade {
    @Autowired
    private PlayerNotificator playerNotificator;

    @Autowired
    private List<Matchmaker<? extends MatchmakingProfile, ? extends MatchConfiguration>> matchmakers;

    public Optional<AssignedMatch> getMatchForPlayer(final String username) {
        return playerNotificator.getMatchForPlayer(Objects.requireNonNull(username));
    }

    public void removePlayerFromMatchmaking(final String username) {
        Objects.requireNonNull(username);

        matchmakers.forEach(matchmaker -> matchmaker.removePlayerFromMatchmaking(username));
    }
}
