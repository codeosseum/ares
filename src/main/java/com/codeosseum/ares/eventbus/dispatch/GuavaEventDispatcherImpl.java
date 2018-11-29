package com.codeosseum.ares.eventbus.dispatch;

import com.google.common.eventbus.EventBus;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class GuavaEventDispatcherImpl implements EventDispatcher {
    private static final String DISPATCH_FAILURE_MESSAGE = "Failed to dispatch event.";

    private final EventBus eventBus;

    public GuavaEventDispatcherImpl(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void dispatchEvent(final Object event) throws EventDispatchFailedException {
        try {
            eventBus.post(Objects.requireNonNull(event));
        } catch (Exception e) {
            throw new EventDispatchFailedException(DISPATCH_FAILURE_MESSAGE, e);
        }
    }

    @Override
    public <E> void registerConsumer(final EventConsumer<E> consumer) {
        eventBus.register(Objects.requireNonNull(consumer));
    }
}
