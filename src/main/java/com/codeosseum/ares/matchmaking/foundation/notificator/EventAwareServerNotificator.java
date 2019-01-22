package com.codeosseum.ares.matchmaking.foundation.notificator;

import com.codeosseum.ares.eventbus.dispatch.EventConsumer;
import com.codeosseum.ares.eventbus.dispatch.EventDispatcher;
import com.codeosseum.ares.matchmaking.foundation.persistence.MatchPersistedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

public class EventAwareServerNotificator implements EventConsumer<MatchPersistedEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(EventAwareServerNotificator.class.getName());

    private static final String MATCH_PATH = "match";

    private final RestTemplate serverCommunicator;

    private final ModeToEndpointTranslator endpointTranslator;

    public EventAwareServerNotificator(final RestTemplate serverCommunicator, final EventDispatcher eventDispatcher, final ModeToEndpointTranslator endpointTranslator) {
        this.serverCommunicator = serverCommunicator;
        this.endpointTranslator = endpointTranslator;

        eventDispatcher.registerConsumer(MatchPersistedEvent.class, this);
    }

    @Override
    public void accept(final MatchPersistedEvent event) {
        final URI baseUri = URI.create(event.getMatch().getServer().getUri());

        try {
            final URI endpoint = endpointTranslator.getEndpointOf(event.getMatch().getMatchConfiguration().getMode())
                .map(baseUri::resolve)
                .orElseThrow(() -> new RuntimeException("TODO: better exception"));

            LOGGER.info("Sending match {} to server {}.", event.getMatch().getId(), event.getMatch().getServer().getIdentifier());

            serverCommunicator.put(endpoint, event.getMatch());
        } catch (Exception e) {
            // Send denied event
        }
    }
}
