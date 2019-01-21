package com.codeosseum.ares.eventbus.controller;

import com.codeosseum.ares.eventbus.dispatch.EventDispatcher;
import com.codeosseum.ares.eventbus.mapper.EventMapper;
import com.codeosseum.ares.web.Paths;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@Controller
public class EventController {
    private static final Logger LOGGER = LoggerFactory.getLogger(EventController.class.getName());

    private static final String EVENT_IDENTIFIER_HEADER = "X-Event-Identifier";

    private final EventMapper eventMapper;

    private final EventDispatcher eventDispatcher;

    public EventController(final EventMapper eventMapper, final EventDispatcher eventDispatcher) {
        this.eventMapper = eventMapper;
        this.eventDispatcher = eventDispatcher;
    }

    @PostMapping(Paths.Api.EVENT)
    public ResponseEntity publishEvent(@RequestBody final String body,
                                       @RequestHeader(EVENT_IDENTIFIER_HEADER) final String identifier) {
        LOGGER.info("Received event with ID: {}", identifier);
        LOGGER.debug("Received event with body: {}", body);

        eventMapper.map(identifier, body).ifPresent(eventDispatcher::dispatchEvent);

        return new ResponseEntity(HttpStatus.OK);
    }
}
