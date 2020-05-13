/*
 * Copyright 2019-2020 Elypia CIC
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

import org.junit.jupiter.api.Test;
import spark.Spark;

import java.net.MalformedURLException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author seth@elypia.org (Syed Shah)
 */
public class ConstructionTest {

    /** Assign a invalid port. */
    @Test
    public void badPort() {
        assertThrows(IllegalArgumentException.class, () -> new Webhooker("https://webhooks.elypia.org/:uuid", -5));
    }

    /**
     * Specify a valid, but incompatible URL.
     * No route named <code>uuid</code>.
     */
    @Test
    public void badUrlNotMalformed() {
        assertThrows(IllegalArgumentException.class, () -> new Webhooker("https://webhooks.elypia.org/", 0));
    }

    /** Specify an valid but incompatible protocol. */
    @Test
    public void badProtocol() {
        assertThrows(IllegalArgumentException.class, () -> new Webhooker("ftp://webhooks.elypia.org/:uuid", 0));
    }

    /**
     * Make sure Webhooker and Spark initialize correctly with the
     * non-default port.
     *
     * @throws MalformedURLException It won't.
     */
    @Test
    public void nonDefaultPort() throws MalformedURLException {
        Webhooker hooker = new Webhooker("http://localhost:4568/:uuid", 4568);
        Spark.awaitInitialization();

        assertEquals(4568, Spark.port());

        hooker.close();
        Spark.awaitStop();
    }
}
