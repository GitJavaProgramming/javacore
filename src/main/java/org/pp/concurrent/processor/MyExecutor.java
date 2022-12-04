package org.pp.concurrent.processor;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class MyExecutor {

    private final Thread monitorThread;
    private final Monitor monitor = new Monitor();
    private final int reserveThreadSize = 0;
    private final int maxThreadSize;
    private final ConcurrentLinkedQueue<TaskThread<Integer>> threadList = new ConcurrentLinkedQueue<>();
    //    private LinkedList<TaskThread<Integer>> expireThreadList = new LinkedList<>();
//    private RejectedExecutionHandler rejectedExecutionHandler;
    private final AtomicInteger exeCount = new AtomicInteger();

    public MyExecutor(int maxThreadSize) {
        this.maxThreadSize = maxThreadSize;
        monitor.addTask(() -> {
            long expireCount = threadList.stream().filter(TaskThread::expire).filter(t -> !t.isStop()).count();
            int limit = maxThreadSize - reserveThreadSize;
//                System.out.println("expireCount=" + expireCount);
//                System.out.println("limit=" + limit);
            if (expireCount > limit) {
                List<TaskThread<Integer>> list = threadList.stream().filter(TaskThread::expire).limit(limit).toList();
                list.forEach(TaskThread::shutdownNow);
                threadList.removeAll(list);
                System.out.println("移除过期线程...");
            }
        });
        monitorThread = new Thread(monitor, "executor-monitor");
        monitorThread.setDaemon(true);
        monitorThread.start();
    }

    public synchronized void execute(Runnable command) {
        exeCount.getAndIncrement();
        if (threadList.isEmpty()) {
            TaskThread<Integer> thread = new TaskThread<>(0);
            threadList.offer(thread);
            thread.submit(command);
        } else {
            TaskThread<Integer> thread = null;
            boolean allRunning = threadList.stream().allMatch(TaskThread::isRunning);
            if (allRunning) {
                int i = threadList.size();
                boolean allFull = threadList.stream().allMatch(TaskThread::taskFull); // 是否所有线程都是满任务
                if (allFull && i < maxThreadSize) {
                    thread = new TaskThread<>(i);
                    threadList.offer(thread);
                } else if (!allFull) {
                    thread = threadList.stream().filter(t -> !t.taskFull()).findAny().get(); // 找一个没满任务的提交
                } else if (i == maxThreadSize) {
                    System.out.println("线程池线程已达最大值，并且都在运行中...");
                }
                if (thread != null) {
                    thread.submit(command);
                }
            } else {
                thread = threadList.stream().filter(TaskThread::waiting).findAny().get();
                thread.submit(command);
            }
        }
    }

    public Collection<TaskThread<Integer>> getCollection() {
        return Collections.unmodifiableCollection(threadList);
    }

    public synchronized void shutdown() {
        threadList.forEach(TaskThread::shutdownGracefully);
//        threadList.forEach(t -> {
//            if (t.isAlive() && !t.isInterrupted()) {
//                t.interrupt();
//            }
//        });
    }

    public synchronized void shutdownGracefully() {
        System.out.println(this.hashCode() + "exeCount = " + exeCount);
        while (exeCount.get() > 0) {
            exeCount.getAndDecrement();
            monitor.addTask(() -> threadList.forEach(TaskThread::shutdownGracefully));
        }
    }

    private class Monitor implements Runnable {

        private final BlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<>(2);

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                blockingQueue.forEach(Runnable::run);
            }
            blockingQueue.clear();
            System.out.println(Thread.currentThread().getName() + "中断了...");
        }

        public void addTask(Runnable runnable) {
            blockingQueue.offer(runnable);
        }
    }
}
