package com.codeosseum.ares.matchmaking.config;

import com.codeosseum.ares.matchmaking.faultseeding.twoplayer.TwoPlayerFaultSeedingMatchConfiguration;
import com.codeosseum.ares.matchmaking.faultseeding.twoplayer.TwoPlayerFaultSeedingMatchmakerImpl;
import com.codeosseum.ares.matchmaking.faultseeding.twoplayer.TwoPlayerFaultSeedingMatchmakingProfile;
import com.codeosseum.ares.matchmaking.foundation.matchmaker.Matchmaker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static java.util.Arrays.asList;

@Configuration
public class MatchmakingConfig {
    @Bean
    public Matchmaker<TwoPlayerFaultSeedingMatchmakingProfile, TwoPlayerFaultSeedingMatchConfiguration> twoPlayerFaultSeedingMatchmaker() {
        return new TwoPlayerFaultSeedingMatchmakerImpl();
    }

    @Bean
    public List<Matchmaker<? extends TwoPlayerFaultSeedingMatchmakingProfile, ? extends TwoPlayerFaultSeedingMatchConfiguration>> matchmakers() {
        return asList(twoPlayerFaultSeedingMatchmaker());
    }
}
