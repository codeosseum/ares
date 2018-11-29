package com.codeosseum.ares.eventbus.mapper;

import java.util.Optional;

public interface EventMapper {
    Optional<Object> map(String identifier, String contents);
}
