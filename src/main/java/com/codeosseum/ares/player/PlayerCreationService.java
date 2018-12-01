package com.codeosseum.ares.player;

public interface PlayerCreationService {
    Player createPlayer(String username) throws PlayerCreationFailedException;
}
