package com.codeosseum.ares.matchmaking.foundation;

import com.codeosseum.ares.matchmaking.foundation.matchmaker.Matchmaker;
import com.codeosseum.ares.matchmaking.foundation.matchmaker.MatchmakingProfile;
import com.codeosseum.ares.matchmaking.foundation.matchmaker.MatchConfiguration;
import com.codeosseum.ares.matchmaking.foundation.notificator.PlayerNotificator;
import com.codeosseum.ares.matchmaking.foundation.persistence.PlayableMatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class MatchmakingFacade {
    private static final Logger LOGGER = LoggerFactory.getLogger(MatchmakingFacade.class.getName());

    @Autowired
    private PlayerNotificator playerNotificator;

    @Autowired
    private List<Matchmaker<? extends MatchmakingProfile, ? extends MatchConfiguration>> matchmakers;

    public Optional<PlayableMatch> getMatchForPlayer(final String username) {
        LOGGER.info("Trying to get match for player: {}", username);

        return playerNotificator.getMatchForPlayer(Objects.requireNonNull(username));
    }

    public void removePlayerFromMatchmaking(final String username) {
        LOGGER.info("Removing player form matchmaking: {}", username);

        Objects.requireNonNull(username);

        matchmakers.forEach(matchmaker -> matchmaker.removePlayerFromMatchmaking(username));
    }
}
