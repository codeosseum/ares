package com.codeosseum.ares.servermanagement.heartbeat;

import com.codeosseum.ares.eventbus.dispatch.EventDispatcher;
import com.codeosseum.ares.eventbus.registry.EventRegistry;
import com.codeosseum.ares.servermanagement.registry.ServerRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.time.Clock;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Configuration
public class HeartbeatConfig {
    private static final int SCHEDULED_THREAD_POOL_SIZE = 1;

    @Autowired
    private EventDispatcher eventDispatcher;

    @Autowired
    private EventRegistry eventRegistry;

    @Autowired
    private ServerRegistry serverRegistry;

    @Autowired
    private HeartbeatProperties heartbeatProperties;

    @Bean
    public ReaperHeartbeatConsumer reaperHeartbeatConsumer() {
        return ReaperHeartbeatConsumer.builder()
                .serverRegistry(serverRegistry)
                .scheduledExecutorService(scheduledExecutorService())
                .clock(Clock.systemDefaultZone())
                .checkSeconds(heartbeatProperties.getCheckSeconds())
                .timeoutSeconds(heartbeatProperties.getTimeoutSeconds())
                .build();
    }

    @PostConstruct
    public void registerEvents() {
        eventRegistry.registerEvent(HeartbeatEvent.IDENTIFIER, HeartbeatEvent.class);
    }

    @PostConstruct
    public void registerConsumers() {
        eventDispatcher.registerConsumer(HeartbeatEvent.class, reaperHeartbeatConsumer());
    }

    private ScheduledExecutorService scheduledExecutorService() {
        return Executors.newScheduledThreadPool(SCHEDULED_THREAD_POOL_SIZE);
    }
}
