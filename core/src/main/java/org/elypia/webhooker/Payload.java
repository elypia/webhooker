/*
 * Copyright 2019-2020 Elypia CIC and Contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.elypia.webhooker;

import com.google.gson.Gson;
import org.elypia.webhooker.controller.ClientController;
import spark.*;

import java.util.*;

/**
 * An event object in Webhooker, this contains the
 * {@link Client}, {@link Request}, and {@link Response} associated
 * with a payload to a valid client.
 *
 * @author seth@elypia.org (Syed Shah)
 */
public class Payload {

    /** The {@link Gson} object provided to {@link Webhooker#getGson() Webhooker}. */
    private final Gson gson;

    /** The never-null client that owns the POST request. */
    private final Client client;

    /** The request that was sent by the client. */
    private final Request request;

    /** The response to return to the client. */
    private final Response response;

    /**
     * @param gson The {@link Gson} object from {@link Webhooker#getGson()}}.
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
