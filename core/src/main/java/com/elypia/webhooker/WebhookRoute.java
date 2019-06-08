package com.elypia.webhooker;

import org.slf4j.*;
import spark.*;

import java.util.*;

/**
 * The route to register to {@link Spark} for webhooks to
 * {@link Spark#post POST} to.
 */
public class WebhookRoute implements Route {

    /** SLF4J Logger */
    private static Logger logger = LoggerFactory.getLogger(WebhookRoute.class);

    /** The WebHook instance this {@link WebhookRoute} is registered to. */
    private final WebHooker webhooker;

    /**
     * @param webhooker The parent {@link WebHooker} instance.
     */
    public WebhookRoute(final WebHooker webhooker) {
        this.webhooker = webhooker;
    }

    /**
     * Handle a POST request made to this webserver.
     *
     * @param request The request that was made by a service.
     * @param response The response to return back to the service.
     * @return The response to return back to the service.
     */
    @Override
    public Object handle(Request request, Response response) {
        String param = request.params("uuid");

        if (param == null)
            throw new IllegalStateException("Request URL doesn't have the route parameter `:uuid`, shouldn't be in this route.");

        UUID uuid;

        try {
            uuid = UUID.fromString(param);
        } catch (IllegalArgumentException ex) {
            logger.warn("Unknown client with non-UUID route parameter `{}` made a post request, ignoring client.", param);
            return null;
        }

        Client client = webhooker.getController().get(uuid);

        if (client == null) {
            logger.warn("Unknown client with UUID `{}` made a post request, ignoring client.", uuid);
            return null;
        }

        Payload payload = new Payload(webhooker.getGson(), client, request, response);
        List<Dispatcher> callbacks = client.getCallbacks();

        for (Dispatcher dispatcher : callbacks) {
            if (!dispatcher.dispatch(payload))
                break;
        }

        return response.body();
    }
}
