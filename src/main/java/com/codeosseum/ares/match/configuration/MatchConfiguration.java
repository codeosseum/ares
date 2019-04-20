package com.codeosseum.ares.match.configuration;

import com.codeosseum.ares.match.factory.DefaultMatchFactoryImpl;
import com.codeosseum.ares.match.factory.MatchFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;
import java.util.UUID;
import java.util.function.Supplier;

@Configuration
public class MatchConfiguration {
    @Bean
    public MatchFactory matchFactory() {
        final Supplier<String> joinPasswordSupplier = () -> UUID.randomUUID().toString();
        final Clock creationClock = Clock.systemDefaultZone();

        return new DefaultMatchFactoryImpl(joinPasswordSupplier, creationClock);
    }
}
