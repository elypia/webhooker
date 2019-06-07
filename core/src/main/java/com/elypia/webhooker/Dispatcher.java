package com.elypia.webhooker;

/**
 * Multiple dispatchers can be assigned to a client
 * dispatchers are how webhook payloads are handled by WebHooker.
 */
@FunctionalInterface
public interface Dispatcher {

    /**
     * A callback to handle this payload, the response
     * of this object is returned to the service that made
     * the POST request.
     *
     * @param payload The client, request and response that represents this payload.
     * @return If {@link WebHooker} should continue handling this payload.
     */
    boolean dispatch(Payload payload);

    /**
     * @return An identifier for the dispatcher for easier
     * (de)seralization of clients to external services.
     */
    default String getId() {
        return this.getClass().getName();
    }
}
