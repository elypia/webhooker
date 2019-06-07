package com.elypia.webhooker;

import java.util.*;

public class Client {

    private final UUID uuid;

    /**
     * A never-null list of dispatchers. Dispatchers are callbacks
     * that are executed in the order they are added.
     */
    private final List<Dispatcher> callbacks;

    public Client(Dispatcher... dispatchers) {
        this(UUID.randomUUID(), dispatchers);
    }

    public Client(String uuid, Dispatcher... dispatchers) {
        this(UUID.fromString(uuid), dispatchers);
    }

    public Client(UUID uuid, Dispatcher... dispatchers) {
        this(uuid, Arrays.asList(dispatchers));
    }

    public Client(UUID uuid, List<Dispatcher> callbacks) {
        this.uuid = Objects.requireNonNull(uuid);
        this.callbacks = (callbacks != null) ? new ArrayList<>(callbacks) : new ArrayList<>();
    }

    public void addCallbacks(Dispatcher... dispatchers) {
        Objects.requireNonNull(dispatchers);
        callbacks.addAll(Arrays.asList(dispatchers));
    }

    public UUID getUuid() {
        return uuid;
    }

    public List<Dispatcher> getCallbacks() {
        return Collections.unmodifiableList(callbacks);
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Client))
            return false;

        return uuid.equals(((Client)object).uuid);
    }
}
