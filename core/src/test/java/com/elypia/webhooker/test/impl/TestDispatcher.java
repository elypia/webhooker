package com.elypia.webhooker.test.impl;

import com.elypia.webhooker.*;

public class TestDispatcher implements Dispatcher {

    @Override
    public boolean dispatch(Payload payload) {
        return true;
    }
}
