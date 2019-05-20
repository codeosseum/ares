package com.codeosseum.ares.web;

public final class Views {
    public static final String HOME = "home";
    public static final String SIGN_IN = "signin";
    public static final String SIGN_UP = "signup";

    public static final class Game {
        private static final String PREFIX = "game/";

        public static final String HOME = PREFIX + "home";

        public static final String MATCHMAKING = PREFIX + "/matchmaking";

        public static final String FAULT_SEEDING = PREFIX + "/fault-seeding";

        private Game() {
            // Cannot be constructed.
        }
    }

    private Views() {
        // Cannot be constructed.
    }
}
