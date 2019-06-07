package com.elypia.webhooker.controller;

import com.elypia.webhooker.*;

import java.util.*;

/**
 * This interface allows an application to flexibly
 * decide how clients are managed, they could be in memory like
 * the {@link MapClientController}, in a database, or Redis, whatever
 * is best fit for the application(s) being developed.
 * WebHooker only cares that one way or another it is able to
 * store and get clients.
 */
public interface ClientController extends Iterable<Client> {

    /**
     * Add a new client to this controller.
     *
     * @param client The new client to add.
     * @return The same client that was added.
     */
    Client add(Client client);

    /**
     * Get a client by UUID.
     *
     * @param uuid The UUID of the client to get.
     * @return The client that is represented by this UUID,
     * or null if no such client exists within the context
     * of this controller.
     */
    Client get(UUID uuid);

    /**
     * @return A list of all clients.
     */
    Map<UUID, Client> getAll();

    /**
     * Create a new client.
     *
     * @return Return the new client that was created.
     */
    default Client add(Dispatcher... callbacks) {
        return add(UUID.randomUUID(), callbacks);
    }

    /**
     * Create a new client.
     *
     * @return Return the new client that was created.
     */
    default Client add(UUID uuid, Dispatcher... callbacks) {
        return add(new Client(uuid, callbacks));
    }

    default Client get(String uuidString) {
        UUID uuid = UUID.fromString(uuidString);
        return get(uuid);
    }

    @Override
    default Iterator<Client> iterator() {
        return getAll().values().iterator();
    }
}
