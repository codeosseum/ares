package com.codeosseum.ares.matchmaking.foundation.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "codeosseum.matchmaking.schedule")
@Data
public class MatchmakingScheduleProperties {
    private int initialDelaySeconds;

    private int delaySeconds;
}
