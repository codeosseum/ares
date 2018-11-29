package com.codeosseum.ares.eventbus.mapper;

import com.codeosseum.ares.eventbus.registry.EventRegistry;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class JacksonEventMapperImpl implements EventMapper {
    private final ObjectMapper eventMapperObjectMapper;

    private final EventRegistry eventRegistry;

    public JacksonEventMapperImpl(final ObjectMapper eventMapperObjectMapper, final EventRegistry eventRegistry) {
        this.eventMapperObjectMapper = eventMapperObjectMapper;
        this.eventRegistry = eventRegistry;
    }

    @Override
    public Optional<Object> map(final String identifier, final String contents) {
        return eventRegistry.getEventForIdentifier(Objects.requireNonNull(identifier))
                .flatMap(type -> {
                    try {
                        return Optional.of(eventMapperObjectMapper.readValue(contents, type));
                    } catch (Exception e){
                        return Optional.empty();
                    }
                });
    }
}
