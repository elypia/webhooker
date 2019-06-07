package com.elypia.webhooker;

import com.elypia.webhooker.controller.ClientController;
import com.google.gson.Gson;
import spark.*;

import java.util.*;

/**
 * An event object in WebHooker, this contains the
 * {@link Client}, {@link Request}, and {@link Response} associated
 * with a payload to a valid client.
 */
public class Payload {

    /** The {@link Gson} object provided to {@link WebHooker#getGson() WebHooker}. */
    private final Gson gson;

    /** The never-null client that owns the POST request. */
    private final Client client;

    /** The request that was sent by the client. */
    private final Request request;

    /** The response to return to the client. */
    private final Response response;

    /**
     * @param gson The {@link Gson} object from {@link WebHooker#getGson()}}.
     * @param client The client from {@link ClientController#get(UUID)}.
     * @param request The request from the POST request.
     * @param response The response that will be sent back after running all callbacks.
     */
    public Payload(Gson gson, Client client, Request request, Response response) {
        this.gson = Objects.requireNonNull(gson);
        this.client = Objects.requireNonNull(client);
        this.request = Objects.requireNonNull(request);
        this.response = Objects.requireNonNull(response);
    }

    public <T> T getRequestBody(Class<T> t) {
        return gson.fromJson(request.body(), t);
    }

    public <T> void setResponseBody(T t) {
        response.body(gson.toJson(t));
    }

    public Client getClient() {
        return client;
    }

    public Request getRequest() {
        return request;
    }

    public Response getResponse() {
        return response;
    }
}
