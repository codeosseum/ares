package com.codeosseum.ares.web;

public final class Views {
    public static final String HOME = "home";
    public static final String LOGIN = "login";
    public static final String REGISTRATION = "registration";

    public static final class Game {
        private static final String PREFIX = "game/";

        public static final String HOME = PREFIX + "home";

        public static final String MATCHMAKING = PREFIX + "/matchmaking";

        private Game() {
            // Cannot be constructed.
        }
    }

    private Views() {
        // Cannot be constructed.
    }
}
