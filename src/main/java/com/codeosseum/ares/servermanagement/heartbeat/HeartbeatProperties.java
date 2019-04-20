package com.codeosseum.ares.servermanagement.heartbeat;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="codeosseum.heartbeat")
@Data
public class HeartbeatProperties {
    private int checkSeconds;

    private int timeoutSeconds;
}
