package com.codeosseum.ares.web;

public final class Paths {
    public static final String HOME = "/";

    public static final String LOGIN = "/login";
    public static final String LOGIN_ERROR = LOGIN + "?error=true";

    public static final String LOGOUT_SUCCESS = HOME + "?logout=true";

    public static final String REGISTRATION = "/registration";

    public static final class Game {
        private static final String PREFIX = "/game";

        public static final String HOME = PREFIX;

        public static final String MATCHMAKING = PREFIX + "/matchmaking";

        private Game() {
            // Cannot be constructed.
        }
    }

    public static final class Api {
        private static final String PREFIX = "/api";

        public static final String REGISTRATION = PREFIX + "/registration";

        public static final String EVENT = PREFIX + "/event";

        public static final String MATCHMAKING = PREFIX + "/matchmaking";

        private Api() {
            // Cannot be constructed.
        }
    }

    private Paths() {
        // Cannot be constructed.
    }
}
