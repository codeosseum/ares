package com.codeosseum.ares.config.web;

/**
 * Utility class storing path and view names used throughout the application.
 */
public final class Endpoints {
    public static final class Paths {
        public static final String HOME = "/";

        private Paths() {
            // Cannot be constructed.
        }
    }

    public static final class Views {
        public static final String HOME = "home";

        private Views() {
            // Cannot be constructed.
        }
    }

    private Endpoints() {
        // Cannot be constructed.
    }
}
