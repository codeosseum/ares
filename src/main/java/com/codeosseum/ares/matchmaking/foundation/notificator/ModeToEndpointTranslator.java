package com.codeosseum.ares.matchmaking.foundation.notificator;

import com.codeosseum.ares.match.Mode;

import java.util.Optional;

public interface ModeToEndpointTranslator {
    Optional<String> getEndpointOf(Mode mode);
}
