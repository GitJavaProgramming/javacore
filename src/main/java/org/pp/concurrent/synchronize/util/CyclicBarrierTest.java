package org.pp.concurrent.synchronize.util;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.IntStream;

public class CyclicBarrierTest {
    public static void main(String[] args) {
        // 一组线程执行，并在栅栏处等待，只要有3个参与者就调度一次
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3, () -> System.out.println("all parties ready."));
        IntStream.range(0, 8).mapToObj(i -> new Thread(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + " await.");
                cyclicBarrier.await(1, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                System.out.println(Thread.currentThread().getName() + " 等待时屏障损坏.");
            } catch (TimeoutException e) {
                System.out.println(Thread.currentThread().getName() + " 等待超时.");
                System.out.println("Parties:" + cyclicBarrier.getParties());
                System.out.println("NumberWaiting:" + cyclicBarrier.getNumberWaiting()); // 0
                System.out.println("isBroken:" + cyclicBarrier.isBroken());
            }
        })).forEach(Thread::start);
    }
}
