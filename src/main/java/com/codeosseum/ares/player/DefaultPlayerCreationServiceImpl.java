package com.codeosseum.ares.player;

import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class DefaultPlayerCreationServiceImpl implements PlayerCreationService {
    private final PlayerRepository playerRepository;

    public DefaultPlayerCreationServiceImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public Player createPlayer(final String username) throws PlayerCreationFailedException {
        final Player newPlayer = emptyPlayerWithUsername(Objects.requireNonNull(username));

        try {
            return playerRepository.save(newPlayer);
        } catch (Exception e) {
            throw new PlayerCreationFailedException(username, e);
        }
    }

    private Player emptyPlayerWithUsername(final String username) {
        return Player.builder()
                .username(username)
                .matchesPlayed(0)
                .matchesWon(0)
                .experience(0)
                .rank(1)
                .build();
    }
}
