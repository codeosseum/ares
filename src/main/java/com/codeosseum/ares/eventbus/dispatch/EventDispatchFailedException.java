package com.codeosseum.ares.eventbus.dispatch;

public class EventDispatchFailedException extends RuntimeException {
    public EventDispatchFailedException(String message) {
        super(message);
    }

    public EventDispatchFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
