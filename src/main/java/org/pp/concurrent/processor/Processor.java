package org.pp.concurrent.processor;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 线程调度处理机
 * Java线程在这里和操作系统的进程调度不一样，Java线程启动后就一直执行，所以我认为没有线程调度的说法，但有线程间通信的讲法
 */
public class Processor {

    // 阻塞线程队列
    // 就绪线程队列
    // 阻塞线程挂起
    // 就绪线程挂起

    public List<TaskThread> ready(int nThreads) {
        List<TaskThread> list = IntStream.range(0, nThreads).mapToObj(key -> new TaskThread(key, new Job(new Task(2))/*每个线程都有一个任务*/)).collect(Collectors.toList());
        return list;
    }

    public void schedule(List<TaskThread> readyQueue) {
        int nThreads = readyQueue.size();
        int[] waitQueue = new int[readyQueue.size()];
        int front = 0, tail = 0, running;
        while (tail < nThreads) {
            waitQueue[tail] = tail;
            tail++;
        }
        System.out.println("waitQueue:" + Arrays.toString(waitQueue));
        System.out.println("front = " + front + ", tail = " + tail);
        int num = 0;
        CountDownLatch latch = null;
        while (num < 3) { // 这里应该是循环调度
            if (front == 0) {
                latch = new CountDownLatch(nThreads);
            }
            if (front != tail) {
                running = waitQueue[front++];
                TaskThread thread = readyQueue.get(running).setLatch(latch);
                if (running > 0) {
                    TaskThread prev = readyQueue.get(running - 1).setLatch(latch);
                    while (true) {
                        if (prev.jobDone()) {
                            thread.wakeup();
                            break;
                        }
                    }
                } else {
                    thread.wakeup();
                }
            } else {
                front = 0;
                readyQueue.forEach(TaskThread::restart); // 任务重启
                try {
                    latch.await();
                } catch (InterruptedException e) {
                    System.out.println("等待所有线程运行完毕");
                }
                System.out.println("调度次数：" + (++num));
            }
        }
        readyQueue.forEach(TaskThread::shutdownNow);
    }

    /**
     * 具体任务
     */
    class Task implements Runnable {

        private static final char[] chars = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz".toCharArray();
        private static int ip;
        private int count = 1; // 一次打印的字符数

        public Task() {
        }

        public Task(int count) {
            this.count = count;
        }

        @Override
        public void run() {
            int tmp = count;
            while (tmp > 0) {
                tmp--;
                System.out.println(Thread.currentThread().getName() + "-" + chars[(ip++) % chars.length]);
                if (ip >= chars.length) {
                    ip = 0;
                }
            }
        }
    }
}
