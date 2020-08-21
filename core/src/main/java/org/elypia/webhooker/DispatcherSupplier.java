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

package org.elypia.webhooker;

import org.elypia.webhooker.controller.ClientController;

import java.util.*;
import java.util.function.Supplier;

/**
 * This is a means to manage and supply dispatchers to your
 * {@link ClientController} that serialize or deseralize the client
 * instances. This is not required by all ClientControllers.
 *
 * @author seth@elypia.org (Syed Shah)
 */
public class DispatcherSupplier {

    private Map<String, Supplier<Dispatcher>> dispatchers;

    public DispatcherSupplier() {
        dispatchers = new HashMap<>();
    }

    /**
     * Add a singleton dispatcher, this will not
     * instantiate a new dispatcher when required but reuse
     * the same one throughout the application lifetime.
     *
     * @param dispatcher The dispatcher to add.
     */
    public void add(Dispatcher dispatcher) {
        add(dispatcher.getId(), () -> dispatcher);
    }

    /**
     * Add a dispatcher to map serialized clients dispatchers to.
     * This will be used when serializing a client to obtain the actual
     * dispatcher.
     *
     * @param id The ID of the dispatcher, the serialized client should store this.
     * @param supplier How to create an instance of this dispatcher.
     */
    public void add(String id, Supplier<Dispatcher> supplier) {
        if (dispatchers.containsKey(id))
            throw new IllegalArgumentException("Can't register two suppliers for same ID.");

        dispatchers.put(id, supplier);
    }

    /**
     * Get an instance of a Dispatcher of which it's supplier was added.
     *
     * @param id The ID of the dispatcher.
     * @return An instantiated dispatcher instance.
     */
    public Dispatcher get(String id) {
        return (dispatchers.containsKey(id)) ? dispatchers.get(id).get() : null;
    }
}
