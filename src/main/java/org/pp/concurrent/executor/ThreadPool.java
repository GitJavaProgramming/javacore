package org.pp.concurrent.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * 1、预置线程
 * 2、任务队列
 * 3、选择线程执行任务--表现为方法调用
 */
public final class ThreadPool implements IThreadPoolService {

    private final List<PoolThread> threads = new ArrayList<>();
    private final int coreThreadNumber;
    private final int maxThreadNumber;

    private final int taskCapacity = 2;
    private final BlockingQueue<Runnable> taskList = new LinkedBlockingQueue<>(taskCapacity);

    private volatile boolean canStop = false;

    private int index = 0; // 索引，用于选择线程

    public ThreadPool(int coreThreadNumber, int maxThreadNumber) {
        this.coreThreadNumber = coreThreadNumber;
        this.maxThreadNumber = maxThreadNumber;
        startup();
    }

    public static void main(String[] args) {
        final ThreadPool threadPool = new ThreadPool(2, 10);
        // 启动
//        threadPool.startup();
        // 任务提交
        IntStream.range(0, 10).mapToObj(i -> (Runnable) () -> {
            System.out.println(Thread.currentThread().getName() + " task run...");
            try {
                TimeUnit.MILLISECONDS.sleep(1);
            } catch (InterruptedException e) {
                Thread.interrupted();
                throw new RuntimeException(Thread.currentThread().getName() + "睡眠被中断，异常转换（异常出现的场景不一样）...");
            }
        }).forEach(threadPool::submit);
//        Thread.currentThread().getThreadGroup().list();
//        threadPool.shutdownNow();
        // 关闭
        threadPool.shutdown();
    }

    /**
     * 提交任务，选择一个内置线程来执行
     */
    public void submit(Runnable task) {
        try {
            taskList.put(task);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 所有的核心线程是否都在执行任务 --> handler(taskList);
        boolean allScheduling = threads.stream().map(t -> t.scheduling).reduce(true, (a, b) -> a && b);
        if (allScheduling) {
            System.out.println("核心线程都在调度执行，任务放入队列，当前队列中任务数:" + taskList.size());
            return;
        }
        PoolThread worker = threads.get(index++ % threads.size()); // 选择工作线程
        if (worker.getState() == Thread.State.NEW) {
            worker.start(); // 启动线程，等待从任务队列中拿任务执行
        }
    }

    public void shutdown() {
        while (!canStop) {
            // 所有的工作线程都调度完成
            canStop = true;
            threads.forEach(t -> canStop &= !t.scheduling);
        }
        // 任务队列中还有任务 --> handler(taskList);
        if (!taskList.isEmpty()) {
            taskList.clear();
        }
        threads.forEach(Thread::interrupt);
    }

    public void shutdownNow() {
        threads.forEach(Thread::interrupt);
    }

    public void startup() {
        // 此处预置线程，jdk的实现是延迟初始化
        IntStream.range(0, coreThreadNumber).mapToObj(i -> new PoolThread("pool-" + i)).forEach(threads::add);
    }

    private class PoolThread extends Thread {

        private volatile boolean working = false;

        private volatile boolean scheduling = false;

        public PoolThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            try {
                while (!isInterrupted()) {
                    Runnable task = taskList.take(); // 阻塞
                    scheduling = true;
                    task.run();
                    working = true; // 工作线程运行标志位
                    scheduling = false;
                }
            } catch (InterruptedException e) {
                System.out.println("空闲线程被中断...");
//                Thread.interrupted();
            } catch (RuntimeException e2) {
                System.out.println(e2.getMessage());
                Thread.interrupted();
            } finally {
                working = false; // 工作线程运行标志位
                System.out.println("run end...");
            }
        }
    }
}
