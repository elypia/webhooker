package com.elypia.webhooker.test;

import com.elypia.webhooker.WebHooker;
import org.junit.jupiter.api.Test;
import spark.Spark;

import java.net.MalformedURLException;

import static org.junit.jupiter.api.Assertions.*;

public class ConstructionTest {

    /** Assign a invalid port. */
    @Test
    public void badPort() {
        assertThrows(IllegalArgumentException.class, () -> new WebHooker("https://webhooks.elypia.com/:uuid", -5));
    }

    /**
     * Specify a valid, but incompatible URL.
     * No route named <code>uuid</code>.
     */
    @Test
    public void badUrlNotMalformed() {
        assertThrows(IllegalArgumentException.class, () -> new WebHooker("https://webhooks.elypia.com/", 0));
    }

    /** Specify an valid but incompatible protocol. */
    @Test
    public void badProtocol() {
        assertThrows(IllegalArgumentException.class, () -> new WebHooker("ftp://webhooks.elypia.com/:uuid", 0));
    }

    /**
     * Make sure WebHooker and Spark initialize correctly with the
     * non-default port.
     *
     * @throws MalformedURLException It won't.
     */
    @Test
    public void nonDefaultPort() throws MalformedURLException {
        WebHooker hooker = new WebHooker("http://localhost:4568/:uuid", 4568);
        Spark.awaitInitialization();

        assertEquals(4568, Spark.port());

        hooker.close();
        Spark.awaitStop();
    }
}
