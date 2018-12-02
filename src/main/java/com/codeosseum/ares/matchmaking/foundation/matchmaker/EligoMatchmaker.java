package com.codeosseum.ares.matchmaking.foundation.matchmaker;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public abstract class EligoMatchmaker<P extends MatchmakingProfile, M extends MatchConfiguration> implements Matchmaker<P, M> {
    private final com.codeosseum.eligo.matchmaker.Matchmaker<P, M> eligoMatchmaker;

    private final Map<String, P> profileMapping;

    public EligoMatchmaker(final com.codeosseum.eligo.matchmaker.Matchmaker<P, M> eligoMatchmaker) {
        this.eligoMatchmaker = eligoMatchmaker;
        this.profileMapping = new ConcurrentHashMap<>();
    }

    public void addProfileToMatchmaking(final P profile) {
        profileMapping.put(Objects.requireNonNull(profile).getUsername(), profile);

        eligoMatchmaker.addPlayer(profile);
    }

    public void removePlayerFromMatchmaking(final String username) {
        Objects.requireNonNull(username);

        Optional.ofNullable(profileMapping.get(username))
                .ifPresent(profile -> {
                    profileMapping.remove(username);
                    eligoMatchmaker.removePlayer(profile);
                });
    }

    public Set<M> makeMatches() {
        return eligoMatchmaker.makeMatchAndKeepPlayers();
    }
}
