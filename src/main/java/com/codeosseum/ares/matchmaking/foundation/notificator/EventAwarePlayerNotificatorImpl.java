package com.codeosseum.ares.matchmaking.foundation.notificator;

import com.codeosseum.ares.eventbus.dispatch.EventDispatcher;
import com.codeosseum.ares.matchmaking.foundation.persistence.MatchPersistedEvent;
import com.codeosseum.ares.matchmaking.foundation.persistence.PlayableMatch;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class EventAwarePlayerNotificatorImpl implements PlayerNotificator {
    private final Map<String, PlayableMatch> matchMap;

    public EventAwarePlayerNotificatorImpl(EventDispatcher eventDispatcher) {
        this.matchMap = new ConcurrentHashMap<>();

        eventDispatcher.registerConsumer(this::consumeMatchMadeEvent);
    }

    @Override
    public Optional<PlayableMatch> getMatchForPlayer(final String username) {
        return Optional.ofNullable(matchMap.get(Objects.requireNonNull(username)));
    }

    private void consumeMatchMadeEvent(final MatchPersistedEvent event) {
        event.getMatch().getMatchConfiguration().getPlayers().forEach(player -> matchMap.put(player, event.getMatch()));
    }
}
