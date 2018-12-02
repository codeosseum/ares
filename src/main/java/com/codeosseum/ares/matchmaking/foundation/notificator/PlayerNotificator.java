package com.codeosseum.ares.matchmaking.foundation.notificator;

import com.codeosseum.ares.matchmaking.foundation.persistence.PlayableMatch;

import java.util.Optional;

public interface PlayerNotificator {
    Optional<PlayableMatch> getMatchForPlayer(String username);
}
