package com.ddu.demo.java.concurrent;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wxl24life
 */
public class ThreadLocalTest {
    private static final AtomicInteger nextId = new AtomicInteger(0);

    private static ThreadLocal<Integer> threadId = new ThreadLocal<Integer>() {
        @Override
        protected Integer initialValue() {
            return nextId.getAndIncrement();
        }
    };

    // Returns the current thread's unique ID, assigning it if necessary
    public static int get() {
        return threadId.get();
    }
}
