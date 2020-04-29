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

package org.elypia.webhooker.controller;

import org.elypia.webhooker.*;

/**
 * Small abstraction, {@link ClientController} that require
 * the {@link DispatcherSupplier} can extend this class
 * instead which will prep it for obtaining Dispatchers
 * from a (de)serialized {@link Dispatcher#getId() dispatcher ID}.
 *
 * @author seth@elypia.org (Syed Shah)
 */
public abstract class SuppliedClientController implements ClientController {

    protected DispatcherSupplier supplier;

    public SuppliedClientController() {
        this.supplier = new DispatcherSupplier();
    }

    public SuppliedClientController(DispatcherSupplier supplier) {
        this.supplier = supplier;
    }

    public DispatcherSupplier getSupplier() {
        return supplier;
    }
}
