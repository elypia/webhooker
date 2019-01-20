package com.elypia.webhooker;

import com.elypia.webhooker.annotation.*;
import com.google.gson.Gson;
import spark.*;

import java.lang.reflect.*;
import java.util.Optional;

public class PayloadDispatcher {

    private WebHooker hooker;
    private Gson gson;

    public PayloadDispatcher(WebHooker hooker) {
        this.hooker = hooker;
        gson = new Gson();
    }

    public void dispatch(Request request, Response response) throws InvocationTargetException, IllegalAccessException {
        Optional<Receiver> optReceiver = hooker.getReceivers().parallelStream()
            .filter((receiver) -> {
                Mapping module = receiver.getClass().getAnnotation(Mapping.class);
                return module.value().equals(request.params("class"));
            }).findAny();

        if (optReceiver.isEmpty())
            return;

        Receiver receiver = optReceiver.get();
        Method[] methods = receiver.getClass().getMethods();

        for (Method method : methods) {
            if (method.isAnnotationPresent(Mapping.class)) {
                Mapping req = method.getAnnotation(Mapping.class);

                if (req.value().equals(request.params("method"))) {
                    int count = method.getParameterCount();

                    switch (count) {
                        case 0: {
                            method.invoke(receiver);
                            break;
                        }
                        case 1: {
                            method.invoke(receiver, new Payload(request, response));
                            break;
                        }
                        case 2: {
                            var object = gson.fromJson(request.body(), method.getParameterTypes()[1]);
                            method.invoke(receiver, new Payload(request, response), object);
                            break;
                        }
                        default: {
                            throw new IllegalStateException("Payload receiving method can only have 0 to 2 parameters.");
                        }
                    }
                }
            }
        }
    }
}
