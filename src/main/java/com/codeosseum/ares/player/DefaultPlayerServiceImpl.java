package com.codeosseum.ares.player;

import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class DefaultPlayerServiceImpl implements PlayerService {
    private final PlayerRepository playerRepository;

    public DefaultPlayerServiceImpl(PlayerRepository playerRepository) {
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

    @Override
    public Optional<Player> findPlayerByUsername(final String username) {
        return playerRepository.findPlayerByUsername(Objects.requireNonNull(username));
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
