package com.codeosseum.ares.player;

public class PlayerCreationFailedException extends Exception {
    private static final String MESSAGE_TEMPLATE = "Failed to register new player: %s";

    private final String username;

    public PlayerCreationFailedException(final String username) {
        super(String.format(MESSAGE_TEMPLATE, username));
        this.username = username;
    }

    public PlayerCreationFailedException(final String username, final Throwable cause) {
        super(String.format(MESSAGE_TEMPLATE, username), cause);

        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
