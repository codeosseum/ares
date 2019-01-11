package com.codeosseum.ares.eventbus.dispatch;

public interface EventDispatcher {
    void dispatchEvent(Object event) throws EventDispatchFailedException;

    <E> void registerConsumer(Class<E> eventType, EventConsumer<E> consumer);
}
