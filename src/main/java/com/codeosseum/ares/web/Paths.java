package com.codeosseum.ares.web;

public final class Paths {
    public static final String HOME = "/";

    public static final String SIGN_IN = "/signin";
    public static final String SIGN_IN_ERROR = SIGN_IN + "?error=true";

    public static final String SIGN_OUT_SUCCESS = HOME + "?logout=true";

    public static final String SIGN_UP = "/signup";

    public static final class Game {
        private static final String PREFIX = "/game";

        public static final String HOME = PREFIX;

        public static final String MATCHMAKING = PREFIX + "/matchmaking";

        public static final String FAULT_SEEDING = PREFIX + "/fault-seeding";

        private Game() {
            // Cannot be constructed.
        }
    }

    public static final class Api {
        private static final String PREFIX = "/api";

        public static final String REGISTRATION = PREFIX + "/signup";

        public static final String EVENT = PREFIX + "/event";

        public static final String MATCHMAKING = PREFIX + "/matchmaking";

        public static final String MATCHMAKING_SUBPATHS = MATCHMAKING + "/**";

        private Api() {
            // Cannot be constructed.
        }
    }

    private Paths() {
        // Cannot be constructed.
    }
}
