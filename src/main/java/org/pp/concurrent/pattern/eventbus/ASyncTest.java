package org.pp.concurrent.pattern.eventbus;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ASyncTest {
    public static void main(String[] args) {
        Bus bus = new AsyncEventBus("Test", (ThreadPoolExecutor) Executors.newFixedThreadPool(10));
        bus.register(new SimpleSubscriber1());
        bus.register(new SimpleSubscriber2());
        bus.post("Hello");
        System.out.println("---------");
        bus.post("Hello", "test");
    }
}
