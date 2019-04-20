package com.codeosseum.ares.matchmaking.foundation.persistence;

import com.codeosseum.ares.eventbus.dispatch.EventConsumer;
import com.codeosseum.ares.eventbus.dispatch.EventDispatcher;
import com.codeosseum.ares.match.Match;
import com.codeosseum.ares.match.Status;
import com.codeosseum.ares.match.repository.MatchRepository;
import com.codeosseum.ares.matchmaking.foundation.matchmaker.MatchAssignedEvent;
import com.codeosseum.ares.servermanagement.Server;
import com.codeosseum.ares.servermanagement.registry.ServerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

import static java.util.Collections.singletonList;

public class EventAwareMatchPersistor implements EventConsumer<MatchAssignedEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(EventAwareMatchPersistor.class.getName());

    private final MatchRepository matchRepository;

    private final ServerRegistry serverRegistry;

    private final EventDispatcher eventDispatcher;

    public EventAwareMatchPersistor(MatchRepository matchRepository, ServerRegistry serverRegistry, EventDispatcher eventDispatcher) {
        this.matchRepository = matchRepository;
        this.serverRegistry = serverRegistry;
        this.eventDispatcher = eventDispatcher;

        eventDispatcher.registerConsumer(MatchAssignedEvent.class, this);
    }

    @Override
    public void accept(final MatchAssignedEvent event) {
        final Match match = createMatchFromMatchMadeEvent(event);

        final MatchPersistedEvent matchPersistedEvent = persistMatch(match, event);

        LOGGER.info("Match (ID = {}) PERSISTED: {}", matchPersistedEvent.getMatch().getId(), matchPersistedEvent.getMatch());

        eventDispatcher.dispatchEvent(matchPersistedEvent);
    }

    private MatchPersistedEvent persistMatch(final Match match, final MatchAssignedEvent event) {
        final Match persistedMatch = matchRepository.save(match);

        final PlayableMatch result = PlayableMatch.builder()
                .id(persistedMatch.getId())
                .matchConfiguration(event.getMatchConfiguration())
                .server(serverForEvent(event))
                .build();

        return new MatchPersistedEvent(result);
    }

    private Server serverForEvent(MatchAssignedEvent event) {
        // TODO: Make this one safer.
        // There can be cases, when the server disconnects before this call is made
        // therefore findByIdentifier yielding empty. The probability of this case
        // is quite small, however, it should be dealt with.
        return serverRegistry.findByIdentifier(event.getAssignedServerIdentifier()).get();
    }

    private Match createMatchFromMatchMadeEvent(final MatchAssignedEvent event) {
        final CreatedMatchEvent createdMatchEvent = new CreatedMatchEvent(LocalDateTime.now(), event.getMatchConfiguration());

        return Match.builder()
                .mode(event.getMatchConfiguration().getMode())
                .players(event.getMatchConfiguration().getPlayers())
                .serverIdentifier(event.getAssignedServerIdentifier())
                .events(singletonList(createdMatchEvent))
                .status(Status.CREATED)
                .build();
    }


}
