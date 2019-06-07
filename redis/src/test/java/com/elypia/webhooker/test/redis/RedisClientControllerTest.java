package com.elypia.webhooker.test.redis;

import com.elypia.webhooker.Client;
import org.junit.jupiter.api.*;
import redis.clients.jedis.Jedis;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

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
