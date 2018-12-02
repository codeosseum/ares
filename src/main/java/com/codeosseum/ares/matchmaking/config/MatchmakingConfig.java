package com.codeosseum.ares.matchmaking.config;

import com.codeosseum.ares.matchmaking.foundation.matchmaker.MatchConfiguration;
import com.codeosseum.ares.matchmaking.foundation.matchmaker.Matchmaker;
import com.codeosseum.ares.matchmaking.foundation.matchmaker.MatchmakingProfile;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static java.util.Collections.emptyList;

@Configuration
public class MatchmakingConfig {
    @Bean
    public List<Matchmaker<? extends MatchmakingProfile, ? extends MatchConfiguration>> matchmakers() {
        return emptyList();
    }
}
