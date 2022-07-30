package org.pp.concurrent.synchronize.util;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class CountDownLatchTest {

    public static void main(String[] args) {
        CountDownLatchTest countDownLatchTest = new CountDownLatchTest();
        CountDownLatch latch = new CountDownLatch(3);
        countDownLatchTest.test(latch, 10);
    }

    public void test(final CountDownLatch latch, final int max) {
        // 工作线程等任务线程执行，只要有3个任务执行完成了就唤醒一组工作线程
        IntStream.range(0, 6).mapToObj(i -> new Thread(new Task("TaskThread-" + i, latch))).forEach(Thread::start);
        IntStream.range(0, max).mapToObj(i -> new Thread(new Worker("WorkerThread-" + i, latch))).forEach(Thread::start);
    }

    class Worker implements Runnable {

        private final String name;
        private final CountDownLatch latch;

        Worker(String name, CountDownLatch latch) {
            this.name = name;
            this.latch = latch;
        }

        @Override
        public void run() {
            try {
                latch.await(); // 阻塞，countDown计数为0时返回
            } catch (InterruptedException e) {
                System.out.println("线程中断...");
            }
            System.out.println(name + " wake up.");
        }
    }

    class Task implements Runnable {

        private final String name;
        private final CountDownLatch latch;

        Task(String name, CountDownLatch latch) {
            this.name = name;
            this.latch = latch;
        }

        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                System.out.println(name + " interrupted.");
            }
            System.out.println(name + " done.");
            latch.countDown();
        }
    }
}
