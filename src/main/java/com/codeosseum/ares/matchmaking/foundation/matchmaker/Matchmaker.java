package com.codeosseum.ares.matchmaking.foundation.matchmaker;

import java.util.Set;

public interface Matchmaker<P extends MatchmakingProfile, M extends MatchmakingResult> {
    void addProfileToMatchmaking(P profile);

    void removePlayerFromMatchmaking(String username);

    Set<M> makeMatches();
}
