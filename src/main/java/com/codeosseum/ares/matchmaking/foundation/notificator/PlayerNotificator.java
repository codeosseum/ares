package com.codeosseum.ares.matchmaking.foundation.notificator;

import com.codeosseum.ares.matchmaking.foundation.matchmaker.AssignedMatch;

import java.util.Optional;

public interface PlayerNotificator {
    Optional<AssignedMatch> getMatchForPlayer(String username);
}
