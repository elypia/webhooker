/*
 * Copyright 2019-2019 Elypia CIC
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

import org.elypia.webhooker.impl.*;
import org.junit.jupiter.api.*;
import spark.*;

import java.net.MalformedURLException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * @author seth@elypia.org (Syed Shah)
 */
public class WebhookerTest {

    private static Webhooker hooker;
    private static TwitchService twitch;
    private static Request request;
    private static Response response;

    @BeforeEach
    public void beforeEach() throws MalformedURLException {
        hooker = new Webhooker("http://localhost:4567/:uuid");

        twitch = new TwitchService();
        Spark.awaitInitialization();

        request = mock(Request.class);
        when(request.requestMethod()).thenReturn("POST");
        when(request.contentType()).thenReturn("application/json");
        when(request.protocol()).thenReturn("http");
        when(request.port()).thenReturn(4567);
        when(request.pathInfo()).thenReturn("/");

        response = mock(Response.class);
    }

    @AfterEach
    public void afterEach() {
        hooker.close();
        Spark.awaitStop();
    }

    @Test
    public void getPublicUrl() {
        assertEquals("http://localhost:4567/:uuid", hooker.getPublicUrl());
    }

    @Test
    public void clientSetResponse() {
        Client client = hooker.getController().add((payload) -> {
            payload.setResponseBody(new Streamer(31415, "Fun is what matters!", true));
            return true;
        });

        when(request.body()).thenReturn("{}");
        when(request.url()).thenReturn(hooker.getPublicUrl(client));
        when(request.params("uuid")).thenReturn(client.getUuid().toString());
        when(response.body()).thenReturn("{\"id\": 31415, \"description\": \"Fun is what matters!\", \"isLive\": true}");

        Object object = hooker.getRoute().handle(request, response);
        assertNotNull(object);
        assertTrue(object instanceof String);

        String body = (String)object;
        Streamer streamer = hooker.getGson().fromJson(body, Streamer.class);

        assertAll("Verify streamer added in responde has same info.",
            () -> assertEquals(31415, streamer.getId()),
            () -> assertEquals("Fun is what matters!", streamer.getDescription()),
            () -> assertTrue(streamer.isLive())
        );
    }

    @Test
    public void fromFalseToTrue() {
        Client client = hooker.getController().add((payload) -> {
            twitch.setAuthorised(true);
            return true;
        });

        when(request.body()).thenReturn("{}");
        when(request.url()).thenReturn(hooker.getPublicUrl(client));
        when(request.params("uuid")).thenReturn(client.getUuid().toString());

        hooker.getRoute().handle(request, response);
        assertTrue(twitch.isAuthorised());
    }

    @Test
    public void setStreamer() {
        Client client = hooker.getController().add((payload) -> {
            Streamer streamer = payload.getRequestBody(Streamer.class);
            twitch.setStreamer(streamer);
            return true;
        });

        when(request.body()).thenReturn("{\"id\": 31415, \"description\": \"Fun is what matters!\", \"isLive\": false}");
        when(request.url()).thenReturn(hooker.getPublicUrl(client));
        when(request.params("uuid")).thenReturn(client.getUuid().toString());

        hooker.getRoute().handle(request, response);
        Streamer streamer = twitch.getStreamer();

        assertAll("That streamer was set to the response succesfully.",
            () -> assertEquals(31415, streamer.getId()),
            () -> assertEquals("Fun is what matters!", streamer.getDescription()),
            () -> assertFalse(streamer.isLive())
        );
    }

    @Test
    public void dispatcherSkipsRest() {
        StringJoiner joiner = new StringJoiner(" ");
        Client client = hooker.getController().add(
                (payload) -> {joiner.add("Hello"); return false;},
                (payload) -> {joiner.add("World!"); return true;}
        );

        when(request.body()).thenReturn("{}");
        when(request.url()).thenReturn(hooker.getPublicUrl(client));
        when(request.params("uuid")).thenReturn(client.getUuid().toString());

        hooker.getRoute().handle(request, response);
        assertEquals("Hello", joiner.toString());
    }

    @Test
    public void fakeUuid() {
        UUID uuid = UUID.randomUUID();

        when(request.body()).thenReturn("{}");
        when(request.url()).thenReturn("http://localhost:4567/" + uuid);
        when(request.params("uuid")).thenReturn(uuid.toString());

        Object object = hooker.getRoute().handle(request, response);
        assertNull(object);
    }

    @Test
    public void noUuid() {
        when(request.body()).thenReturn("{}");
        when(request.url()).thenReturn("http://localhost:4567/this_isnt_a_uuid");
        when(request.params("uuid")).thenReturn("this_isnt_a_uuid");

        Object object = hooker.getRoute().handle(request, response);
        assertNull(object);
    }
}
