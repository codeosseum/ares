package com.codeosseum.ares.servermanagement.heartbeat;

import com.codeosseum.ares.eventbus.dispatch.EventConsumer;
import com.codeosseum.ares.servermanagement.registry.ServerRegistry;
import lombok.Builder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Clock;
import java.time.LocalTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static java.time.temporal.ChronoUnit.SECONDS;

@Builder
public class ReaperHeartbeatConsumer implements EventConsumer<HeartbeatEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReaperHeartbeatConsumer.class);

    private final ServerRegistry serverRegistry;

    private final ScheduledExecutorService scheduledExecutorService;

    private final Clock clock;

    private final long checkSeconds;

    private final long timeoutSeconds;

    private final Map<String, LocalTime> heartbeatTimeMap = new ConcurrentHashMap<>();

    public ReaperHeartbeatConsumer(ServerRegistry serverRegistry, ScheduledExecutorService scheduledExecutorService, Clock clock, long checkSeconds, long timeoutSeconds) {
        this.serverRegistry = serverRegistry;
        this.scheduledExecutorService = scheduledExecutorService;
        this.clock = clock;
        this.checkSeconds = checkSeconds;
        this.timeoutSeconds = timeoutSeconds;

        this.setupReaperProcess();
    }

    @Override
    public void accept(final HeartbeatEvent heartbeatEvent) {
        LOGGER.info("Received heartbeat from {}", heartbeatEvent.getServerIdentifier());

        heartbeatTimeMap.put(heartbeatEvent.getServerIdentifier(), LocalTime.now(clock));
    }

    private void setupReaperProcess() {
        scheduledExecutorService.scheduleWithFixedDelay(this::reapServers, 0L, checkSeconds, TimeUnit.SECONDS);
    }

    private void reapServers() {
        final LocalTime checkTime = LocalTime.now(clock);

        heartbeatTimeMap.entrySet()
                .removeIf(entry -> {
                    final LocalTime lastHeartbeat = entry.getValue();

                    final boolean shouldRemove = SECONDS.between(lastHeartbeat, checkTime) > timeoutSeconds;

                    if (shouldRemove) {
                        LOGGER.info("Removing server because it's not sending heartbeats: {}", entry.getKey());
                    }

                    return shouldRemove;
                });
    }
}
