package org.pp.concurrent.synchronize.lock;

import java.util.Collection;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LockFairnessTest {

    // 公平性体现在获取锁等待时间上，在获取锁的时间顺序上对线程排队，获取锁的顺序不依赖线程启动顺序
    private final ReentrantLock2 fairSyncLock = new ReentrantLock2(true);
    private final ReentrantLock2 nonFairSyncLock = new ReentrantLock2(false);

    public static void main(String[] args) {
        LockFairnessTest lockFairnessTest = new LockFairnessTest();
        lockFairnessTest.test();
    }

    public void test() {
        Resource resource = new Resource(nonFairSyncLock);
        IntStream.rangeClosed(0, 4).mapToObj(t -> new Thread(resource)).forEach(Thread::start);
    }

    final class ReentrantLock2 extends ReentrantLock {
        public ReentrantLock2() {
        }

        public ReentrantLock2(boolean fair) {
            super(fair);
        }

        @Override
        public Collection<Thread> getQueuedThreads() {
            return super.getQueuedThreads();
        }
    }

    class Resource implements Runnable {

        private ReentrantLock2 lock;

        public Resource(ReentrantLock2 lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            lockTest(lock);
        }

        private void lockTest(ReentrantLock2 lock) {
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + " hold lock, queued thread:" + lock.getQueuedThreads().stream().map(t -> t.getName()).collect(Collectors.joining(" -> ")));
            } finally {
                lock.unlock();
            }
        }
    }
}
