package com.codeosseum.ares.match.factory;

import com.codeosseum.ares.match.Match;
import com.codeosseum.ares.matchmaking.foundation.matchmaker.MatchAssignedEvent;

public interface MatchFactory {
    Match createMatchFromEvent(MatchAssignedEvent event);
}
