package com.codeosseum.ares.eventbus.config;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
public class EventBusConfig {
    @Autowired
    public EventBusProperties eventBusProperties;

    @Bean
    public EventBus eventBus() {
        return new AsyncEventBus(eventBusExecutor());
    }

    private Executor eventBusExecutor() {
        return Executors.newFixedThreadPool(eventBusProperties.getExecutorThreadCount());
    }
}
