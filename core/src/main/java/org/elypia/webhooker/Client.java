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

package org.elypia.webhooker;

import java.util.*;

/**
 * @author seth@elypia.org (Syed Shah)
 */
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
