/*
 * Copyright 2019-2020 Elypia CIC
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

import org.elypia.webhooker.impl.TestDispatcher;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author seth@elypia.org (Syed Shah)
 */
public class DispatcherSupplierTest {

    private static DispatcherSupplier supplier;

    @BeforeEach
    public void beforeEach() {
        supplier = new DispatcherSupplier();
    }

    @Test
    public void getDispatcher() {
        TestDispatcher dispatcher = new TestDispatcher();
        assertEquals("org.elypia.webhooker.impl.TestDispatcher", dispatcher.getId());
    }

    @Test
    public void addAndGetDispatcher() {
        TestDispatcher dispatcher = new TestDispatcher();
        supplier.add(dispatcher);
        Dispatcher dispatcher2 = supplier.get("org.elypia.webhooker.impl.TestDispatcher");
        assertEquals(dispatcher, dispatcher2);
    }

    @Test
    public void addSameIdTwice() {
        supplier.add(new TestDispatcher());
        assertThrows(IllegalArgumentException.class, () -> supplier.add(new TestDispatcher()));
    }

    @Test
    public void getNonAddedDispatcher() {
        assertNull(supplier.get("aaaa"));
    }
}
