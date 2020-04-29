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

package org.elypia.webhooker.redis;

import org.elypia.webhooker.Client;
import org.elypia.webhooker.*;
import org.elypia.webhooker.controller.SuppliedClientController;
import redis.clients.jedis.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author seth@elypia.org (Syed Shah)
 */
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
