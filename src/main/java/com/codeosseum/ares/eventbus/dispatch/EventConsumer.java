package com.codeosseum.ares.eventbus.dispatch;

import com.google.common.eventbus.Subscribe;

import java.util.function.Consumer;

@FunctionalInterface
public interface EventConsumer<E> extends Consumer<E> {
    @Subscribe
    default void consume(final E event) {
        this.accept(event);
    }
}
