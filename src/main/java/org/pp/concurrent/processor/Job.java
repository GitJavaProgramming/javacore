package org.pp.concurrent.processor;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * 作业
 */
public class Job {
    private final int maxTask = 2; // 最大任务数
    private final LinkedList<Runnable> taskQueue = new LinkedList<>(); // 每个作业维护一个任务队列
    private final Queue<PriorityRunnable> priorityTaskQueue = new PriorityQueue<>(maxTask);
    private Runnable runnable;
    private volatile boolean done; // 任务是否执行完
    private Status status;

    public Job(Runnable runnable) {
        this.runnable = runnable;
    }

    public void startup() {
        while (!done) {
            runnable.run(); // first
            if (taskQueue.isEmpty()) {
                close();
            } else {
                runnable = taskQueue.remove(); // 同一个线程，任务按队列顺序执行
            }
        }
    }

    public void submit(PriorityRunnable runnable/*提交有优先级的任务*/) {

    }

    public int getMaxTask() {
        return maxTask;
    }

    public LinkedList<Runnable> getTaskQueue() {
        return taskQueue;
    }

    public void submit(Runnable runnable) {
        if (done) {
            this.runnable = runnable;
            restart();
        } else {
            taskQueue.offer(runnable);
        }
    }


    public boolean taskFull() {
        return maxTask == taskQueue.size();
    }

    public Runnable getRunnable() {
        return runnable;
    }

    public boolean isDone() {
        return done;
    }

    public void restart() {
        done = false;
    }

    public void close() {
        done = true;
    }

    private enum Status {
        RESERVE, RUNNING, DONE;
    }

    interface PriorityRunnable<K extends Comparable<K>/*按K比较*/> extends Runnable, Comparable<PriorityRunnable<K>> {

    }
}
