package com.elypia.webhooker.test.redis.impl;

import com.elypia.webhooker.*;

public class UselessDispatcher implements Dispatcher {

    @Override
    public boolean dispatch(Payload payload) {
        return true;
    }
}
