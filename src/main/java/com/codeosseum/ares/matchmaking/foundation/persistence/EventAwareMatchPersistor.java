package com.codeosseum.ares.matchmaking.foundation.persistence;

import com.codeosseum.ares.eventbus.dispatch.EventConsumer;
import com.codeosseum.ares.eventbus.dispatch.EventDispatcher;
import com.codeosseum.ares.match.Match;
import com.codeosseum.ares.match.Status;
import com.codeosseum.ares.match.repository.MatchRepository;
import com.codeosseum.ares.matchmaking.foundation.matchmaker.MatchAssignedEvent;

import java.time.LocalDateTime;

import static java.util.Collections.singletonList;

public class EventAwareMatchPersistor implements EventConsumer<MatchAssignedEvent> {
    private final MatchRepository matchRepository;

    private final EventDispatcher eventDispatcher;

    public EventAwareMatchPersistor(MatchRepository matchRepository, EventDispatcher eventDispatcher) {
        this.matchRepository = matchRepository;
        this.eventDispatcher = eventDispatcher;

        eventDispatcher.registerConsumer(MatchAssignedEvent.class, this);
    }

    @Override
    public void accept(final MatchAssignedEvent event) {
        final Match match = createMatchFromMatchMadeEvent(event);

        final MatchPersistedEvent matchPersistedEvent = persistMatch(match, event);

        eventDispatcher.dispatchEvent(matchPersistedEvent);
    }

    private MatchPersistedEvent persistMatch(final Match match, final MatchAssignedEvent event) {
        final Match persistedMatch = matchRepository.save(match);

        final PlayableMatch result = PlayableMatch.builder()
                .id(persistedMatch.getId())
                .matchConfiguration(event.getMatchConfiguration())
                .server(event.getAssignedServer())
                .build();

        return new MatchPersistedEvent(result);
    }

    private Match createMatchFromMatchMadeEvent(final MatchAssignedEvent event) {
        final CreatedMatchEvent createdMatchEvent = new CreatedMatchEvent(LocalDateTime.now(), event.getMatchConfiguration());

        return Match.builder()
                .mode(event.getMatchConfiguration().getMode())
                .players(event.getMatchConfiguration().getPlayers())
                .serverUri(event.getAssignedServer().getUri())
                .events(singletonList(createdMatchEvent))
                .status(Status.CREATED)
                .build();
    }


}
