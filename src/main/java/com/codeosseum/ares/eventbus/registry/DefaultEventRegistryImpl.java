package com.codeosseum.ares.eventbus.registry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class DefaultEventRegistryImpl implements EventRegistry {
    private final Logger LOGGER = LoggerFactory.getLogger(DefaultEventRegistryImpl.class.getName());

    private final Map<String, Class<?>> typeMap;

    public DefaultEventRegistryImpl() {
        this.typeMap = new ConcurrentHashMap<>();
    }

    @Override
    public void registerEvent(final String identifier, final Class<?> type) {
        LOGGER.info("Registering event type {} for ID {}", type, identifier);

        typeMap.put(Objects.requireNonNull(identifier), Objects.requireNonNull(type));
    }

    @Override
    public void unregisterEvent(final String identifier) {
        LOGGER.info("Unregistering event for ID {}", identifier);

        typeMap.remove(Objects.requireNonNull(identifier));
    }

    @Override
    public Optional<Class<?>> getEventForIdentifier(final String identifier) {
        LOGGER.debug("Translating event ID for type: {}", identifier);
        
        return Optional.ofNullable(typeMap.get(Objects.requireNonNull(identifier)));
    }
}
