package com.codeosseum.ares.matchmaking.foundation.notificator;

import com.codeosseum.ares.match.Mode;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

import static com.codeosseum.ares.match.Mode.TWO_PLAYER_FAULT_SEEDING;

@Component
public class EnumMapModeToEndpointTranslatorImpl implements ModeToEndpointTranslator {
    private static final String PREFIX = "/match/";

    private static final Map<Mode, String> translationMap = new EnumMap<Mode, String>(Mode.class);

    static {
        translationMap.put(TWO_PLAYER_FAULT_SEEDING, "fault-seeding");

        translationMap.replaceAll((mode, fragment) -> PREFIX + fragment);
    }

    @Override
    public Optional<String> getEndpointOf(final Mode mode) {
        return Optional.of(translationMap.get(mode));
    }
}
