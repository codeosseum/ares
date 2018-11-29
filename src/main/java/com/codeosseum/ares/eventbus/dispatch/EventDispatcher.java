package com.codeosseum.ares.eventbus.dispatch;

public interface EventDispatcher {
    void dispatchEvent(Object event) throws EventDispatchFailedException;

    <E> void registerConsumer(EventConsumer<E> consumer);
}
