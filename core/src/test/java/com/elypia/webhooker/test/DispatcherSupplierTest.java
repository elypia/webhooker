package com.elypia.webhooker.test;

import com.elypia.webhooker.*;
import com.elypia.webhooker.test.impl.TestDispatcher;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class DispatcherSupplierTest {

    private static DispatcherSupplier supplier;

    @BeforeEach
    public void beforeEach() {
        supplier = new DispatcherSupplier();
    }

    @Test
    public void getDispatcher() {
        TestDispatcher dispatcher = new TestDispatcher();
        assertEquals("com.elypia.webhooker.test.impl.TestDispatcher", dispatcher.getId());
    }

    @Test
    public void addAndGetDispatcher() {
        TestDispatcher dispatcher = new TestDispatcher();
        supplier.add(dispatcher);
        Dispatcher dispatcher2 = supplier.get("com.elypia.webhooker.test.impl.TestDispatcher");
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
