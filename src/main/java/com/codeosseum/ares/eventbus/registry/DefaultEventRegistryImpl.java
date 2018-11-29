package com.codeosseum.ares.eventbus.registry;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class DefaultEventRegistryImpl implements EventRegistry {
    private final Map<String, Class<?>> typeMap;

    public DefaultEventRegistryImpl() {
        this.typeMap = new ConcurrentHashMap<>();
    }

    @Override
    public void registerEvent(final String identifier, final Class<?> type) {
        typeMap.put(Objects.requireNonNull(identifier), Objects.requireNonNull(type));
    }

    @Override
    public void unregisterEvent(final String identifier) {
        typeMap.remove(Objects.requireNonNull(identifier));
    }

    @Override
    public Optional<Class<?>> getEventForIdentifier(final String identifier) {
        return Optional.ofNullable(typeMap.get(Objects.requireNonNull(identifier)));
    }
}
