package com.elypia.webhooker.test;

import com.elypia.webhooker.*;
import com.elypia.webhooker.test.impl.*;
import okhttp3.*;
import org.junit.jupiter.api.*;
import spark.Spark;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class WebHookerTest {

    private static final MediaType MEDIA_TYPE = MediaType.get("application/json");

    private static WebHooker hooker;
    private static OkHttpClient httpClient;
    private static TwitchService twitch;

    @BeforeEach
    public void beforeEach() throws MalformedURLException {
        hooker = new WebHooker("http://localhost:4567/:uuid");
        httpClient = new OkHttpClient();
        twitch = new TwitchService();
        Spark.awaitInitialization();
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
    public void clientSetResponse() throws IOException {
        Client client = hooker.getController().add((payload) -> {
            payload.setResponseBody(new Streamer(31415, "Fun is what matters!", true));
            return true;
        });

        Request request = new Request.Builder()
            .method("POST", RequestBody.create(MEDIA_TYPE, "{}"))
            .url(hooker.getPublicUrl(client))
            .build();

        Response response = httpClient.newCall(request).execute();
        ResponseBody responseBody = response.body();

        assertNotNull(responseBody);

        String body = responseBody.string();
        Streamer streamer = hooker.getGson().fromJson(body, Streamer.class);

        assertAll("Verify streamer added in responde has same info.",
            () -> assertEquals(31415, streamer.getId()),
            () -> assertEquals("Fun is what matters!", streamer.getDescription()),
            () -> assertTrue(streamer.isLive())
        );
    }

    @Test
    public void fromFalseToTrue() throws IOException {
        Client client = hooker.getController().add((payload) -> {
            twitch.setAuthorised(true);
            return true;
        });

        Request request = new Request.Builder()
            .method("POST", RequestBody.create(MEDIA_TYPE, "{}"))
            .url(hooker.getPublicUrl(client))
            .build();

        httpClient.newCall(request).execute();
        assertTrue(twitch.isAuthorised());
    }

    @Test
    public void setStreamer() throws IOException {
        Client client = hooker.getController().add((payload) -> {
            Streamer streamer = payload.getRequestBody(Streamer.class);
            twitch.setStreamer(streamer);
            return true;
        });

        String reqBody = "{\"id\": 31415, \"description\": \"Fun is what matters!\", \"isLive\": false}";

        Request request = new Request.Builder()
            .method("POST", RequestBody.create(MEDIA_TYPE, reqBody))
            .url(hooker.getPublicUrl(client))
            .build();

        httpClient.newCall(request).execute();
        Streamer streamer = twitch.getStreamer();

        assertAll("That streamer was set to the response succesfully.",
            () -> assertEquals(31415, streamer.getId()),
            () -> assertEquals("Fun is what matters!", streamer.getDescription()),
            () -> assertFalse(streamer.isLive())
        );
    }

    @Test
    public void dispatcherSkipsRest() throws IOException {
        StringJoiner joiner = new StringJoiner(" ");
        Client client = hooker.getController().add(
            (payload) -> {joiner.add("Hello"); return false;},
            (payload) -> {joiner.add("World!"); return true;}
        );

        Request request = new Request.Builder()
            .method("POST", RequestBody.create(MEDIA_TYPE, "{}"))
            .url(hooker.getPublicUrl(client))
            .build();

        httpClient.newCall(request).execute();
        assertEquals("Hello", joiner.toString());
    }

    @Test
    public void fakeUuid() throws IOException {
        Request request = new Request.Builder()
            .method("POST", RequestBody.create(MEDIA_TYPE, "{}"))
            .url("http://localhost:4567/" + UUID.randomUUID())
            .build();

        ResponseBody response = httpClient.newCall(request).execute().body();
        assertNotNull(response);
        assertEquals(-1, response.contentLength());
    }

    @Test
    public void noUuid() throws IOException {
        Request request = new Request.Builder()
            .method("POST", RequestBody.create(MEDIA_TYPE, "{}"))
            .url("http://localhost:4567/this_isnt_a_uuid")
            .build();

        ResponseBody response = httpClient.newCall(request).execute().body();
        assertNotNull(response);
        assertEquals(-1, response.contentLength());
    }
}
