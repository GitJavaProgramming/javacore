package org.pp.concurrent.synchronize.lock;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ArrayBlockingQueue源码就使用了Condition
 */
public class ConditionTest {
    private static volatile boolean flag = true;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();
    private LinkedList<String> queue = new LinkedList<>();

    public void put(String e) throws InterruptedException {
        lock.lock();
        try {
            while (queue.size() > 10) {
                System.out.println("队列满");
                condition.await();
            }
            TimeUnit.MILLISECONDS.sleep(100);
            queue.add(e);
            System.out.println("add " + e);
            condition.signal();
        } finally {
            lock.unlock();
        }
    }

    public void take() throws InterruptedException {
        lock.lock();
        try {
            while (queue.isEmpty()) {
                System.out.println("队列空");
                condition.await();
            }
            TimeUnit.MILLISECONDS.sleep(100);
            String item = queue.poll();
            System.out.println("poll item = " + item);
            condition.signal();
        } finally {
            lock.unlock();
        }
    }

    public static void shutdown() {
        flag = false;
    }

    public static void main(String[] args) throws InterruptedException {
        ConditionTest conditionTest = new ConditionTest();
        Thread t = new Thread(() -> {
            int i = 0;
            while (flag && !Thread.currentThread().isInterrupted()) {
                try {
                    conditionTest.put(String.valueOf(i++));
                } catch (InterruptedException interruptedException) {
                    System.out.println(Thread.currentThread().getName() + " interrupted.");
                    Thread.interrupted();
                }
            }
        });
        t.start();
        Thread t2 = new Thread(() -> {
            while (flag && !Thread.currentThread().isInterrupted()) {
                try {
                    conditionTest.take();
                } catch (InterruptedException e) {
                    System.out.println(Thread.currentThread().getName() + " interrupted.");
                    Thread.interrupted();
                }
            }
        });
        t2.start();
        TimeUnit.SECONDS.sleep(2);
//        t.interrupt();
//        t2.interrupt();
        shutdown();
    }
}
