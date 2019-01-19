package com.elypia.webhooker;

import spark.*;

public class Payload {

    private Request request;
    private Response response;

    public Payload(Request request, Response response) {
        this.request = request;
        this.response = response;
    }

    public Request getRequest() {
        return request;
    }

    public Response getResponse() {
        return response;
    }
}
