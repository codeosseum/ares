package com.codeosseum.ares.match.factory;

import com.codeosseum.ares.match.Match;
import com.codeosseum.ares.match.Status;
import com.codeosseum.ares.matchmaking.foundation.matchmaker.MatchAssignedEvent;
import com.codeosseum.ares.matchmaking.foundation.persistence.CreatedMatchEvent;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.function.Supplier;

import static java.util.Collections.singletonList;

public class DefaultMatchFactoryImpl implements MatchFactory {
    private final Supplier<String> joinPasswordSupplier;

    private final Clock creationClock;

    public DefaultMatchFactoryImpl(final Supplier<String> joinPasswordSupplier, final Clock creationClock) {
        this.joinPasswordSupplier = joinPasswordSupplier;
        this.creationClock = creationClock;
    }

    @Override
    public Match createMatchFromEvent(final MatchAssignedEvent event) {
        final CreatedMatchEvent createdMatchEvent = new CreatedMatchEvent(now(), event.getMatchConfiguration());

        return Match.builder()
                .mode(event.getMatchConfiguration().getMode())
                .players(event.getMatchConfiguration().getPlayers())
                .serverIdentifier(event.getAssignedServerIdentifier())
                .events(singletonList(createdMatchEvent))
                .status(Status.CREATED)
                .joinPassword(joinPasswordSupplier.get())
                .build();
    }

    private LocalDateTime now() {
        return LocalDateTime.ofInstant(creationClock.instant(), ZoneId.systemDefault());
    }
}
