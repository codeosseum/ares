package com.codeosseum.ares.eventbus.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "codeosseum.eventbus")
@Data
public class EventBusProperties {
    private int executorThreadCount;
}
