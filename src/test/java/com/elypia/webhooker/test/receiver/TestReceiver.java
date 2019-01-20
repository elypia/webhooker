package com.elypia.webhooker.test.receiver;

import com.elypia.webhooker.*;
import com.elypia.webhooker.annotation.Mapping;
import com.elypia.webhooker.test.entity.Player;

@Mapping("test")
public class TestReceiver extends Receiver {

    private boolean thisStartsFalse;
    private Player player;

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

    @Mapping("player")
    public void player(Payload payload, Player player) {
        this.player = player;
    }

    public boolean getThisStartsFalse() {
        return thisStartsFalse;
    }

    public Player getPlayer() {
        return player;
    }
}
