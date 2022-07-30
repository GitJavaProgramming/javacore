package org.pp.concurrent.synchronize.lock;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.IntStream;

public class RWLockTest {

    public static void main(String[] args) {
        try {
            new RWLockTest().test();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void test() throws InterruptedException {
        Cache cache = new Cache();
        int N = 10, M = 3;
        IntStream.range(0, N).mapToObj(i -> new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " write.");
            cache.write(i, i);
        })).forEach(Thread::start);
        TimeUnit.SECONDS.sleep(1);
        AtomicInteger ctl = new AtomicInteger();
        IntStream.range(0, M).mapToObj(i -> new Thread(() -> {
            while (true) {
                int pr = ctl.getAndIncrement(); // 每个读线程执行次数不确定
                if (pr < N) {
                    System.out.println(Thread.currentThread().getName() + " read = " + pr + "->" + cache.read(pr));
                } else {
                    break;
                }
            }
        })).forEach(Thread::start);
    }

    private final class Cache {
        private final ReadWriteLock rwLock = new ReentrantReadWriteLock();
        private final Lock readLock = rwLock.readLock();
        private final Lock writeLock = rwLock.writeLock();

        private final HashMap<Integer, Integer> cache = new HashMap<>();

        private Integer read(Integer key) {
            readLock.lock();
            try {
                return cache.get(key);
            } finally {
                readLock.unlock();
            }
        }

        private void write(Integer key, Integer value) {
            writeLock.lock();
            try {
                cache.put(key, value);
            } finally {
                writeLock.unlock();
            }
        }

    }
}
