package com.codeosseum.ares.eventbus.registry;

import java.util.Optional;

public interface EventRegistry {
    void registerEvent(String identifier, Class<?> type);

    void unregisterEvent(String identifier);

    Optional<Class<?>> getEventForIdentifier(String identifier);
}
