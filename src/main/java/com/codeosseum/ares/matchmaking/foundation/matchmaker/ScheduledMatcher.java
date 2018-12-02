package com.codeosseum.ares.matchmaking.foundation.matchmaker;

import com.codeosseum.ares.eventbus.dispatch.EventDispatcher;
import com.codeosseum.ares.matchmaking.foundation.serverallocation.ServerAllocator;
import com.codeosseum.ares.servermanagement.Server;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class ScheduledMatcher {
    private final ServerAllocator serverAllocator;

    private final EventDispatcher eventDispatcher;

    private final List<Matchmaker<? extends MatchmakingProfile, ? extends MatchConfiguration>> matchmakers;

    public ScheduledMatcher(ServerAllocator serverAllocator,
                            EventDispatcher eventDispatcher,
                            List<Matchmaker<? extends MatchmakingProfile, ? extends MatchConfiguration>> matchmakers,
                            ScheduledExecutorService executorService) {
        this.serverAllocator = serverAllocator;
        this.eventDispatcher = eventDispatcher;
        this.matchmakers = matchmakers;

        executorService.scheduleWithFixedDelay(this::checkForMatches, 0L, 10, TimeUnit.SECONDS);
    }

    private void checkForMatches() {
        final List<Server> availableServers = serverAllocator.findAllAvailableServers();

        final List<MatchConfiguration> possibleMatches = createAllPossibleMatches();

        final int creatableMatchCount = Math.min(availableServers.size(), possibleMatches.size());

        final Set<String> assignedPlayers = new HashSet<>();

        for (int i = 0; i < creatableMatchCount; ++i) {
            final MatchConfiguration match = possibleMatches.get(i);
            final Server server = availableServers.remove(i);

            if (allPlayersAreUnassigned(assignedPlayers, match)) {
                assignedPlayers.addAll(match.getPlayers());

                publishMatch(match, server);
            }
        }
    }

    private List<MatchConfiguration> createAllPossibleMatches() {
        return matchmakers.stream()
                .map(Matchmaker::makeMatches)
                .flatMap(Set::stream)
                .collect(Collectors.toList());
    }

    private boolean allPlayersAreUnassigned(final Set<String> assignedPlayers, final MatchConfiguration match) {
        return match.getPlayers().stream()
                .noneMatch(assignedPlayers::contains);
    }

    private void publishMatch(final MatchConfiguration match, final Server server) {
        final AssignedMatch assignedMatch = new AssignedMatch(match, server, LocalDateTime.now());

        final MatchAssignedEvent event = new MatchAssignedEvent(assignedMatch);

        eventDispatcher.dispatchEvent(event);
    }
}
