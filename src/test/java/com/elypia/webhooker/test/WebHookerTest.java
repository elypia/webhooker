package com.elypia.webhooker.test;

import com.elypia.webhooker.WebHooker;
import com.elypia.webhooker.test.receiver.TestReceiver;
import okhttp3.*;
import org.junit.jupiter.api.*;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class WebHookerTest {

    private static final MediaType MEDIA_TYPE = MediaType.get("application/json");

    private static OkHttpClient client;
    private static WebHooker hooker;

    @BeforeAll
    public static void beforeAll() throws IOException {
        hooker = new WebHooker("http://localhost/");
    }

    @BeforeEach
    public void beforeEach() {
        client = new OkHttpClient();
    }

    @Test
    public void fromFalseToTrue() throws IOException {
        TestReceiver receiver = new TestReceiver();
        hooker.add(receiver);

        Request request = new Request.Builder()
            .method("POST", RequestBody.create(MEDIA_TYPE, "{}"))
            .url(hooker.getUrl("test", "make_it_true"))
            .build();

        client.newCall(request).execute();
        boolean actual = receiver.getThisStartsFalse();
        assertTrue(actual);
    }

    @Test
    public void getRespone() throws IOException {
        TestReceiver receiver = new TestReceiver();
        hooker.add(receiver);

        Request request = new Request.Builder()
            .method("POST", RequestBody.create(MEDIA_TYPE, "{}"))
            .url(hooker.getUrl("test", "requires_response"))
            .build();

        Response response = client.newCall(request).execute();

        ResponseBody body = response.body();
        assertNotNull(body);

        String actual = body.string();
        assertEquals("response", actual);
    }
}
