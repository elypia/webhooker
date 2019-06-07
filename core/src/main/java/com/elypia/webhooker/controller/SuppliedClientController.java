package com.elypia.webhooker.controller;

import com.elypia.webhooker.*;

/**
 * Small abstraction, {@link ClientController} that require
 * the {@link DispatcherSupplier} can extend this class
 * instead which will prep it for obtaining Dispatchers
 * from a (de)serialized {@link Dispatcher#getId() dispatcher ID}.
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
