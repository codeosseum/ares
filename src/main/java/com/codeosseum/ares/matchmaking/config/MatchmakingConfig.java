package com.codeosseum.ares.matchmaking.config;

import javax.annotation.PostConstruct;

import com.codeosseum.ares.eventbus.dispatch.EventDispatcher;
import com.codeosseum.ares.eventbus.registry.EventRegistry;
import com.codeosseum.ares.matchmaking.faultseeding.flow.over.MatchOverEvent;
import com.codeosseum.ares.matchmaking.faultseeding.flow.over.MatchOverListener;
import com.codeosseum.ares.matchmaking.faultseeding.twoplayer.TwoPlayerFaultSeedingMatchConfiguration;
import com.codeosseum.ares.matchmaking.faultseeding.twoplayer.TwoPlayerFaultSeedingMatchmakerImpl;
import com.codeosseum.ares.matchmaking.faultseeding.twoplayer.TwoPlayerFaultSeedingMatchmakingProfile;
import com.codeosseum.ares.matchmaking.foundation.matchmaker.Matchmaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static java.util.Arrays.asList;

@Configuration
public class MatchmakingConfig {
    @Autowired
    private EventRegistry eventRegistry;

    @Autowired
    private EventDispatcher eventDispatcher;

    @Autowired
    private MatchOverListener matchOverListener;

    @Bean
    public Matchmaker<TwoPlayerFaultSeedingMatchmakingProfile, TwoPlayerFaultSeedingMatchConfiguration> twoPlayerFaultSeedingMatchmaker() {
        return new TwoPlayerFaultSeedingMatchmakerImpl();
    }

    @Bean
    public List<Matchmaker<? extends TwoPlayerFaultSeedingMatchmakingProfile, ? extends TwoPlayerFaultSeedingMatchConfiguration>> matchmakers() {
        return asList(twoPlayerFaultSeedingMatchmaker());
    }

    @PostConstruct
    public void registerEvents() {
        eventRegistry.registerEvent(MatchOverEvent.IDENTIFIER, MatchOverEvent.class);
    }

    @PostConstruct
    public void registerConsumers() {
        eventDispatcher.registerConsumer(MatchOverEvent.class, matchOverListener);
    }
}
