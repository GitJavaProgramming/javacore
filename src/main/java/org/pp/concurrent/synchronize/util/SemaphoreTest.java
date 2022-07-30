package org.pp.concurrent.synchronize.util;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class SemaphoreTest {
    public static void main(String[] args) {
        new SemaphoreTest().test(10);
    }

    private void test(final int max) {
        // 控制并发数，有多少个线程可以同时进入同步块
        Semaphore semaphore = new Semaphore(max, true);
        IntStream.range(0, max).mapToObj(i -> new Thread(new Task("CThread-" + i, semaphore, max))).forEach(Thread::start);
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        semaphore.release(max);
    }

    class Task implements Runnable {

        private final String name;
        private final Semaphore semaphore;
        private final int maxThreadSize;
//        private volatile int index;

        public Task(String name, Semaphore semaphore, int maxThreadSize) {
            this.name = name;
            this.semaphore = semaphore;
            this.maxThreadSize = maxThreadSize;
        }

        @Override
        public void run() {
            try {
                semaphore.acquire();
                System.out.println(name + "获得许可. 剩余许可数：" + semaphore.availablePermits());
            } catch (InterruptedException e) {
                System.out.println("中断..");
            } finally {
//                System.out.println(name + "释放许可.");
//                semaphore.release();
            }
        }
    }
}
