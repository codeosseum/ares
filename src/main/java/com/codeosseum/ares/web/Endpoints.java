package com.codeosseum.ares.web;

/**
 * Utility class storing path and view names used throughout the application.
 */
public final class Endpoints {
    public static final class Paths {
        public static final String HOME = "/";

        public static final String LOGIN = "/login";
        public static final String LOGIN_ERROR = LOGIN + "?error=true";

        public static final String LOGOUT_SUCCESS = HOME + "?logout=true";

        public static final String REGISTRATION = "/registration";

        public static final class Game {
            private static final String PREFIX = "/game";

            public static final String HOME = PREFIX;

            private Game() {
                // Cannot be constructed.
            }
        }

        public static final class Api {
            private static final String PREFIX = "/api";

            public static final String REGISTRATION = PREFIX + "/registration";

            private Api() {
                // Cannot be constructed.
            }
        }

        private Paths() {
            // Cannot be constructed.
        }
    }

    public static final class Views {
        public static final String HOME = "home";
        public static final String LOGIN = "login";
        public static final String REGISTRATION = "registration";

        public static final class Game {
            private static final String PREFIX = "game/";

            public static final String HOME = PREFIX + "home";

            private Game() {
                // Cannot be constructed.
            }
        }

        private Views() {
            // Cannot be constructed.
        }
    }

    private Endpoints() {
        // Cannot be constructed.
    }
}
