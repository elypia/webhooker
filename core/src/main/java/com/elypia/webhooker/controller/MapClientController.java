package com.elypia.webhooker.controller;

import com.elypia.webhooker.Client;

import java.util.*;

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
