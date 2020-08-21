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

package org.elypia.webhooker.controller;

import org.elypia.webhooker.Client;

import java.util.*;

/**
 * @author seth@elypia.org (Syed Shah)
 */
public class MapClientController implements ClientController {

    private final Map<UUID, Client> clients;

    public MapClientController() {
        clients = new HashMap<>();
    }

    /**
     * Add an existing client to the client pool.
     *
     * @param client The existing client that should be added.
     */
    @Override
    public Client add(Client client) {
        clients.put(client.getUuid(), client);
        return client;
    }

    @Override
    public Client get(UUID uuid) {
        return clients.get(uuid);
    }

    /**
     * @return An unmodifiable view of all clients.
     */
    @Override
    public Map<UUID, Client> getAll() {
        return Collections.unmodifiableMap(clients);
    }

    @Override
    public Iterator<Client> iterator() {
        return clients.values().iterator();
    }
}
