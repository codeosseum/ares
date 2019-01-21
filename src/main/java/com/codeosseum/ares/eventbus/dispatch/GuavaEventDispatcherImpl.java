package com.codeosseum.ares.eventbus.dispatch;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.codeosseum.ares.eventbus.dispatch.GuavaEventDispatcherImpl.TypeSafeEventConsumerWrapper.wrap;

@Service
public class GuavaEventDispatcherImpl implements EventDispatcher {
    private static final Logger LOGGER = LoggerFactory.getLogger(GuavaEventDispatcherImpl.class.getName());

    private static final String DISPATCH_FAILURE_MESSAGE = "Failed to dispatch event.";

    private final EventBus eventBus;

    public GuavaEventDispatcherImpl(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void dispatchEvent(final Object event) throws EventDispatchFailedException {
        try {
            LOGGER.debug("Dispatching event {}", event);

            eventBus.post(Objects.requireNonNull(event));
        } catch (Exception e) {
            throw new EventDispatchFailedException(DISPATCH_FAILURE_MESSAGE, e);
        }
    }

    @Override
    public <E> void registerConsumer(final Class<E> eventType, final EventConsumer<E> consumer) {
        // Note, that this wrapping places a higher load on EventBus, thanks to type erasure.
        // Using a generic wrapper, the EventBus only sees Objects, therefore all subscribers are registered
        // as listeners of the Object.class.
        // When dispatching an event, the EventBus has to loop through ALL subscribers.
        // Currently, the main benefit of this approach (avoiding the Guava EventBus lock-in) outweighs
        // this issue, however, this might become a bottleneck.
        LOGGER.info("Registering event consumer for eventType: {}", eventType);

        eventBus.register(wrap(Objects.requireNonNull(eventType), Objects.requireNonNull(consumer)));
    }

    public static final class TypeSafeEventConsumerWrapper<E> {
        private final Class<E> eventType;

        private final EventConsumer<E> consumer;

        public static <E> TypeSafeEventConsumerWrapper<E> wrap(final Class<E> eventType, final EventConsumer<E> consumer) {
            return new TypeSafeEventConsumerWrapper<>(eventType, consumer);
        }

        private TypeSafeEventConsumerWrapper(final Class<E> eventType, final EventConsumer<E> consumer) {
            this.eventType = eventType;
            this.consumer = consumer;
        }

        @Subscribe
        public void handler(final E event) {
            // In an ideal world (without type erasure) this check would not be
            // necessary.
            if (eventType.equals(event.getClass())) {
                consumer.accept(event);
            }
        }
    }
}
