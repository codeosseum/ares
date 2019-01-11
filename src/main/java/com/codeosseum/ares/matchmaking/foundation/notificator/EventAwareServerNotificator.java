package com.codeosseum.ares.matchmaking.foundation.notificator;

import com.codeosseum.ares.eventbus.dispatch.EventConsumer;
import com.codeosseum.ares.eventbus.dispatch.EventDispatcher;
import com.codeosseum.ares.matchmaking.foundation.persistence.MatchPersistedEvent;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

public class EventAwareServerNotificator implements EventConsumer<MatchPersistedEvent> {
    private static final String MATCH_PATH = "match";

    private final RestTemplate serverCommunicator;

    private final EventDispatcher eventDispatcher;

    public EventAwareServerNotificator(RestTemplate serverCommunicator, EventDispatcher eventDispatcher) {
        this.serverCommunicator = serverCommunicator;
        this.eventDispatcher = eventDispatcher;

        eventDispatcher.registerConsumer(MatchPersistedEvent.class, this);
    }

    @Override
    public void accept(final MatchPersistedEvent event) {
        final URI baseUri = URI.create(event.getMatch().getServer().getUri());

        final URI endpoint = baseUri.resolve(MATCH_PATH);

        try {
            serverCommunicator.put(endpoint, event.getMatch());
        } catch (Exception e) {
            // Send denied event
        }
    }
}