package com.codeosseum.ares.matchmaking.foundation.persistence;

import com.codeosseum.ares.match.MatchEvent;
import com.codeosseum.ares.matchmaking.foundation.matchmaker.MatchConfiguration;

import java.time.LocalDateTime;

public class CreatedMatchEvent extends MatchEvent  {
    private MatchConfiguration matchConfiguration;

    public CreatedMatchEvent(LocalDateTime timestamp, MatchConfiguration matchConfiguration) {
        super(timestamp);
        this.matchConfiguration = matchConfiguration;
    }

    public MatchConfiguration getMatchConfiguration() {
        return matchConfiguration;
    }
}
