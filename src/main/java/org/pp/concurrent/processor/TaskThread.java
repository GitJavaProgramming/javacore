package org.pp.concurrent.processor;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.LockSupport;

/**
 * 线程--作业调度器 线程调度由客户端处理机算法控制
 */
public class TaskThread<K extends Comparable<K>> extends Thread implements Comparable<TaskThread<K>/*线程通过关键字K比较*/> {

    private final K key;
    private final Job job;
    private CountDownLatch latch;
    private volatile boolean stop;
    private volatile boolean running;
    private long startTime = System.currentTimeMillis();
    private final long keepAlive = 3; // 指定线程空闲多少秒可以关闭

    public TaskThread(K key) {
        this.key = key;
        this.job = new Job(() -> System.out.println("job ready, default runnable."));
        setName("任务线程" + key);
        start();
    }

    public TaskThread(K key, Runnable runnable) {
        this.key = key;
        this.job = new Job(runnable);
        setName("任务线程" + key);
        start();
    }

    public TaskThread(K key, Job job) {
        this.key = key;
        this.job = job;
        setName("任务线程" + key);
        start();
    }

    public void run() {
        while (!isInterrupted()) {
            startTime = System.currentTimeMillis();
            LockSupport.park(); // park被唤醒或者被中断，线程继续执行
            if (!stop) { // 中断后是否云许继续执行
                job.startup();
                if (latch != null) {
                    latch.countDown();
                }
            }
        }
    }

    public boolean expire() {
        long currTime = System.currentTimeMillis();
        long keepAliveTime = currTime - startTime;
        // 3秒空闲就关闭任务线程
        return (keepAliveTime / 1000) > keepAlive && job.isDone();
    }

    public long getStartTime() {
        return startTime;
    }

    public boolean waiting() {
        return this.getState() == State.TIMED_WAITING;
    }

    public boolean isRunning() {
        return !waiting();
    }

    public void submit(Runnable runnable) {
        if (job.getMaxTask() == job.getTaskQueue().size()) { // 任务队列满了就丢弃
            System.out.println(getName() + "队列满...");
            return;
        }
        job.submit(runnable);
        wakeup();
    }

    public boolean taskFull() {
        return job.taskFull();
    }

    public boolean jobDone() {
        return job.isDone();
    }

    public TaskThread setLatch(CountDownLatch latch) {
        this.latch = latch;
        return this;
    }

    public void shutdownGracefully() {
        while (true) {
            if (jobDone()) {
                shutdownNow();
                break;
            }
//            try {
//                System.out.println(jobDone());
//                TimeUnit.SECONDS.sleep(1);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }
    }

    public void shutdownNow() {
        System.out.println(getName() + " shutdown...");
        shutdownNow(true);
    }

    public void shutdownNow(boolean stopNow) {
        stop = stopNow;
        interrupt();
    }

    public boolean isStop() {
        return stop;
    }

    public void wakeup() {
        LockSupport.unpark(this);
    }

    public void restart() {
        job.restart();
    }

    @Override
    public int compareTo(TaskThread<K> o) {
        return this.key.compareTo(o.key);
    }
}
