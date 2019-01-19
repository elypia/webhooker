package com.elypia.webhooker.annotation;

import com.elypia.webhooker.Payload;

public abstract class Receiver {

    /**
     * Perform this before the actual event that should be
     * processed. Useful for if an API wants similar actions
     * done on payloads on webhook creation.
     *
     * @return If to continue processing after this.
     */
    public boolean beforeAny(Payload payload) {
        return true;
    }
}
