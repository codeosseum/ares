package com.codeosseum.ares.matchmaking.foundation.config;

import com.codeosseum.ares.eventbus.dispatch.EventDispatcher;
import com.codeosseum.ares.eventbus.registry.EventRegistry;
import com.codeosseum.ares.match.repository.MatchRepository;
import com.codeosseum.ares.matchmaking.foundation.matchmaker.MatchAssignedEvent;
import com.codeosseum.ares.matchmaking.foundation.matchmaker.MatchConfiguration;
import com.codeosseum.ares.matchmaking.foundation.matchmaker.Matchmaker;
import com.codeosseum.ares.matchmaking.foundation.matchmaker.MatchmakingProfile;
import com.codeosseum.ares.matchmaking.foundation.matchmaker.RemovePlayersFromMatchmakingListener;
import com.codeosseum.ares.matchmaking.foundation.matchmaker.ScheduledMatcher;
import com.codeosseum.ares.matchmaking.foundation.notificator.EventAwareServerNotificator;
import com.codeosseum.ares.matchmaking.foundation.notificator.ModeToEndpointTranslator;
import com.codeosseum.ares.matchmaking.foundation.persistence.EventAwareMatchPersistor;
import com.codeosseum.ares.matchmaking.foundation.persistence.MatchPersistedEvent;
import com.codeosseum.ares.matchmaking.foundation.serverallocation.EventAwareServerAllocatorImpl;
import com.codeosseum.ares.matchmaking.foundation.serverallocation.ServerAllocator;
import com.codeosseum.ares.servermanagement.registry.ServerRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Configuration
public class FoundationConfig {
    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private EventDispatcher eventDispatcher;

    @Autowired
    private EventRegistry eventRegistry;

    @Autowired
    private ServerRegistry serverRegistry;

    @Autowired
    private ModeToEndpointTranslator endpointTranslator;

    @Autowired
    private RemovePlayersFromMatchmakingListener removePlayersFromMatchmakingListener;

    @Autowired
    private List<Matchmaker<? extends MatchmakingProfile, ? extends MatchConfiguration>> matchmakers;

    @Bean
    public EventAwareServerNotificator eventAwareServerNotificator() {
        return new EventAwareServerNotificator(serverCommunicator(), eventDispatcher, endpointTranslator);
    }

    @Bean
    public EventAwareMatchPersistor eventAwareMatchPersistor() {
        return new EventAwareMatchPersistor(matchRepository, eventDispatcher);
    }

    @Bean
    public ServerAllocator serverAllocator() {
        return new EventAwareServerAllocatorImpl(serverRegistry, eventDispatcher);
    }

    @Bean
    public ScheduledMatcher scheduledMatcher() {
        final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

        return new ScheduledMatcher(serverAllocator(), eventDispatcher, matchmakers, executorService);
    }

    @PostConstruct
    public void registerEvents() {
        eventRegistry.registerEvent(MatchAssignedEvent.IDENTIFIER, MatchAssignedEvent.class);
        eventRegistry.registerEvent(MatchPersistedEvent.IDENTIFIER, MatchPersistedEvent.class);
    }

    @PostConstruct
    public void registerConsumers() {
        eventDispatcher.registerConsumer(MatchPersistedEvent.class, removePlayersFromMatchmakingListener);
    }

    private RestTemplate serverCommunicator() {
        return new RestTemplate();
    }
}
