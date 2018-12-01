package com.codeosseum.ares.player;

import java.util.Optional;

public interface PlayerService {
    Player createPlayer(String username) throws PlayerCreationFailedException;

    Optional<Player> findPlayerByUsername(String username);
}
