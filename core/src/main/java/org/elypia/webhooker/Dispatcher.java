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

package org.elypia.webhooker;

/**
 * Multiple dispatchers can be assigned to a client
 * dispatchers are how webhook payloads are handled by WebHooker.
 *
 * @author seth@elypia.org (Syed Shah)
 */
@FunctionalInterface
public interface Dispatcher {

    /**
     * A callback to handle this payload, the response
     * of this object is returned to the service that made
     * the POST request.
     *
     * @param payload The client, request and response that represents this payload.
     * @return If {@link WebHooker} should continue handling this payload.
     */
    boolean dispatch(Payload payload);

    /**
     * @return An identifier for the dispatcher for easier
     * (de)seralization of clients to external services.
     */
    default String getId() {
        return this.getClass().getName();
    }
}
