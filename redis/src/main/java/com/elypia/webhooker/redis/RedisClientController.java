package com.elypia.webhooker.redis;

import com.elypia.webhooker.Client;
import com.elypia.webhooker.*;
import com.elypia.webhooker.controller.SuppliedClientController;
import redis.clients.jedis.*;

import java.util.*;
import java.util.stream.Collectors;

public class RedisClientController extends SuppliedClientController {

    /** The connection to Redis. */
    private Jedis jedis;

    public RedisClientController(JedisShardInfo info) {
        this(new Jedis(info));
    }

    public RedisClientController(Jedis jedis) {
        this.jedis = jedis;
    }

    @Override
    public Client add(Client client) {
        String key = client.getUuid().toString();
        String[] dispatchers = client.getCallbacks().stream()
            .map(Dispatcher::getId)
            .toArray(String[]::new);
        jedis.rpush(key, dispatchers);
        return client;
    }

    @Override
    public Client get(UUID uuid) {
        String key = uuid.toString();
        List<Dispatcher> values = jedis.lrange(key, 0, Long.MAX_VALUE).stream()
            .map(supplier::get)
            .collect(Collectors.toUnmodifiableList());

        return new Client(uuid, values);
    }

    @Override
    public Map<UUID, Client> getAll() {
        Set<String> keys = jedis.keys("*");
        return keys.stream()
            .map(this::get)
            .collect(Collectors.toUnmodifiableMap(Client::getUuid, client -> client));
    }

    public Jedis getJedis() {
        return jedis;
    }
}
