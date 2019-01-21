package com.codeosseum.ares.matchmaking.foundation.notificator;

import com.codeosseum.ares.eventbus.dispatch.EventDispatcher;
import com.codeosseum.ares.matchmaking.foundation.persistence.MatchPersistedEvent;
import com.codeosseum.ares.matchmaking.foundation.persistence.PlayableMatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class EventAwarePlayerNotificatorImpl implements PlayerNotificator {
    private static final Logger LOGGER = LoggerFactory.getLogger(EventAwarePlayerNotificatorImpl.class.getName());

    private final Map<String, PlayableMatch> matchMap;

    public EventAwarePlayerNotificatorImpl(EventDispatcher eventDispatcher) {
        this.matchMap = new ConcurrentHashMap<>();

        eventDispatcher.registerConsumer(MatchPersistedEvent.class, this::consumeMatchPersistedEvent);
    }

    @Override
    public Optional<PlayableMatch> getMatchForPlayer(final String username) {
        return Optional.ofNullable(matchMap.get(Objects.requireNonNull(username)));
    }

    private void consumeMatchPersistedEvent(final MatchPersistedEvent event) {
        LOGGER.info("Storing match (ID = ) for players: {}", event.getMatch().getId(), event.getMatch().getMatchConfiguration().getPlayers());

        event.getMatch().getMatchConfiguration().getPlayers().forEach(player -> matchMap.put(player, event.getMatch()));
    }
}
