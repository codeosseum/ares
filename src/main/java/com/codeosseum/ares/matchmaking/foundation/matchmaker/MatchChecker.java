package com.codeosseum.ares.matchmaking.foundation.matchmaker;

import com.codeosseum.ares.eventbus.dispatch.EventDispatcher;
import com.codeosseum.ares.matchmaking.foundation.serverallocation.ServerAllocator;
import lombok.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class MatchChecker {
    private static final Logger LOGGER = LoggerFactory.getLogger(MatchChecker.class);

    private final ServerAllocator serverAllocator;

    private final EventDispatcher eventDispatcher;

    private final List<Matchmaker<? extends MatchmakingProfile, ? extends MatchConfiguration>> matchmakers;

    public MatchChecker(ServerAllocator serverAllocator,
                        EventDispatcher eventDispatcher,
                        List<Matchmaker<? extends MatchmakingProfile, ? extends MatchConfiguration>> matchmakers) {
        this.serverAllocator = serverAllocator;
        this.eventDispatcher = eventDispatcher;
        this.matchmakers = matchmakers;
    }

    public void checkForMatches() {
        LOGGER.info("Checking for matches...");

        final List<MatchConfiguration> possibleMatches = createAllPossibleMatches();

        LOGGER.debug("Possible matches: {}", possibleMatches);

        final Set<String> assignedPlayers = new HashSet<>();

        possibleMatches.stream()
                .map(this::tryAllocateServerForMatch)
                .filter(MatchWithServer::hasServerAllocated)
                .filter(matchWithServer -> allPlayersAreUnassigned(assignedPlayers, matchWithServer.match))
                .forEach(matchWithServer -> {
                    assignedPlayers.addAll(matchWithServer.match.getPlayers());

                    publishMatchAssigned(matchWithServer);
                });
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

    private void publishMatchAssigned(final MatchWithServer matchWithServer) {
        final String serverIdentifier = matchWithServer.serverIdentifier.get();

        LOGGER.info("Match ASSIGNED: {} | Server: {}", matchWithServer.match, serverIdentifier);

        final MatchAssignedEvent event = new MatchAssignedEvent(matchWithServer.match, serverIdentifier);

        eventDispatcher.dispatchEvent(event);
    }

    private MatchWithServer tryAllocateServerForMatch(final MatchConfiguration matchConfiguration) {
        return new MatchWithServer(matchConfiguration, serverAllocator.allocate());
    }

    @Value
    private static final class MatchWithServer {
        private final MatchConfiguration match;

        private final Optional<String> serverIdentifier;

        private boolean hasServerAllocated() {
            return serverIdentifier.isPresent();
        }
    }
}
