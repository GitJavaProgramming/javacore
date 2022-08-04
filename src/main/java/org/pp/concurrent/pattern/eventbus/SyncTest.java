package org.pp.concurrent.pattern.eventbus;

public class SyncTest {
    public static void main(String[] args) {
        Bus bus = new EventBus("Test");
        bus.register(new SimpleSubscriber1());
        bus.register(new SimpleSubscriber2());
        bus.post("Hello");
        System.out.println("---------");
        bus.post("Hello", "test");
    }
}
