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

package org.elypia.webhooker.redis;

import org.elypia.webhooker.Client;
import org.junit.jupiter.api.*;
import redis.clients.jedis.Jedis;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * @author seth@elypia.org (Syed Shah)
 */
public class RedisClientControllerTest {

    private static RedisClientController controller;

    /** Mock Jedis instance so we don't connect to a real Redis instance. */
    private static Jedis jedis;

    @BeforeEach
    public void beforeEach() {
        jedis = mock(Jedis.class);
        controller = new RedisClientController(jedis);
    }

    @Test
    public void addClient() {
        Client client = new Client();
        Client client2 = controller.add(client);
        assertEquals(client, client2);
    }

    @Test
    public void getClient() {
        UUID uuid = UUID.randomUUID();
        Client client = new Client(uuid);
        controller.add(client);

        when(jedis.lrange(uuid.toString(), 0, Long.MAX_VALUE)).thenReturn(List.of());
        Client client2 = controller.get(uuid);

        assertEquals(client, client2);
        verify(jedis, times(1)).lrange(uuid.toString(), 0, Long.MAX_VALUE);
    }
}
