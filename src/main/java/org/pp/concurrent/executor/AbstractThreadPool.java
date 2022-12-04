package org.pp.concurrent.executor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class AbstractThreadPool implements IThreadPoolService {

    private final BlockingQueue<Runnable> blockingQueue;
    private final int corePoolThreadSize;
    private final AtomicInteger ctl = new AtomicInteger(); // 当前工作线程数

    public AbstractThreadPool(int corePoolThreadSize, BlockingQueue<Runnable> blockingQueue) {
        this.blockingQueue = blockingQueue;
        this.corePoolThreadSize = corePoolThreadSize;
    }

    @Override
    public void startup() {

    }

    @Override
    public void submit(Runnable task) {
        if (ctl.getAndIncrement() < corePoolThreadSize) {
            new Thread(task).start();
            return;
        }
        if (blockingQueue.isEmpty()) {
            blockingQueue.add(task);
        }
    }

    @Override
    public void shutdown() {

    }
}
