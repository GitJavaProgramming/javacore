package org.pp.concurrent.synchronize.lock;

import java.util.concurrent.locks.LockSupport;

public class LockSupportTest {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("main run here...");
        Thread t = new Thread(() -> {
            String name = Thread.currentThread().getName();
            System.out.println(name + " parking...");
            LockSupport.park();
            System.out.println(name + " out...");
        });
        t.start();
//        TimeUnit.SECONDS.sleep(2);
        System.out.println("thread started, main wakeup.");
        LockSupport.unpark(t);
    }
}
