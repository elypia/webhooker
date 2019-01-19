package com.elypia.webhooker.test.receiver;

import com.elypia.webhooker.Payload;
import com.elypia.webhooker.annotation.*;

@Mapping("test")
public class TestReceiver extends Receiver {

    private boolean thisStartsFalse;

    public TestReceiver() {
        thisStartsFalse = false;
    }

    @Mapping("make_it_true")
    public void test() {
        thisStartsFalse = true;
    }

    @Mapping("requires_response")
    public void respond(Payload payload) {
        payload.getResponse().body("response");
    }

    public boolean getThisStartsFalse() {
        return thisStartsFalse;
    }
}
