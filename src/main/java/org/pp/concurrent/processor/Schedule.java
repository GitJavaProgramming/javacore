package org.pp.concurrent.processor;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * N个线程，打印AaBb...Zz，M遍
 * 0-A
 * 1-a
 * 2-B
 * ...
 * 0-Y
 * 1-y
 * 2-Z
 * 0-z
 * 0-A  ?  1-A  ?
 * 1-a  ?  2-a  ?
 * <p>
 * Java中线程都是抢占式的
 * 非抢占式调度 运行期间独占cpu 通过控制线程执行的顺序来模拟
 */
public class Schedule {

    /**
     * 等待队列长度
     */
    static final int MAXLEN = 10;
    static final Lock lock = new ReentrantLock();
    /**
     * 执行次数
     */
    static int num = 10;
    static volatile boolean stop;

    public static void main(String[] args) throws InterruptedException, BrokenBarrierException {
        // 10个线程
        List<Process> processes = Arrays.asList(
                new Process(0/*线程编号*/, 1/*打印多少个字符*/, 0),
                new Process(1, 2, 0),
                new Process(2, 3, 0),
                new Process(3, 1, 0),
                new Process(4, 1, 0),
                new Process(5, 1, 0),
                new Process(6, 1, 0),
                new Process(7, 1, 0),
                new Process(8, 1, 0),
                new Process(9, 1, 0));

        Thread monitor = new Thread(() -> { // 监控程序，需要获取监控的对象的引用做处理工作
            while (!Process.stop/*监控到此结束*/) {
                if (Process.num.get() <= 0) {
                    System.out.println(Thread.currentThread().getName() + "检测到资源耗尽...");
                    // 如何找到Process类的所有实例？？
                }
            }
            try {
                TimeUnit.SECONDS.sleep(20); // 模拟耗时操作
            } catch (InterruptedException e) {
                System.out.println("守护线程中断...");
            }
            System.out.println("监控线程monitor停止运行...");
        }, "监控线程monitor");
        monitor.setDaemon(true); // 当运行的唯一线程都是守护程序线程时，Java虚拟机将退出。
        monitor.start();

        FCFS(processes);
        stopProcess(processes);
    }

    public static void FCFS(List<Process> processes) throws InterruptedException, BrokenBarrierException {
        int[] waitQueue = new int[MAXLEN];
        int front = 0;
        int tail = 0;
        int running;
        for (int i = 0; i < processes.size(); i++) {
            waitQueue[tail++] = i;
        }
        processes.forEach(Thread::start);
        System.out.println("等待队列指针：" + Arrays.toString(waitQueue));
        System.out.println("front = " + front + ", tail = " + tail);
        CountDownLatch latch = null;
        while (!stop && num > 0) {
            if (front == 0) {
                latch = new CountDownLatch(10);
            }
            if (front != tail) {
                running = waitQueue[front++];
                Process process = processes.get(running).setLatch(latch);
                if (running > 0) {
                    Process prev = processes.get(running - 1).setLatch(latch);
                    while (true) {
                        if (prev.isDone()) { // 等前一个线程执行完成 保证线程执行顺序
                            LockSupport.unpark(process);
                            break;
                        } else {
                            Thread.onSpinWait();
                        }
                    }
                } else {
                    LockSupport.unpark(process);
                }
            } else {
                front = 0;
                num--;
                latch.await(); // 等待所有线程执行完成
                processes.forEach(t -> t.setDone(false));
                if (Process.stop) {
                    shutdown();
                } else {
                    System.out.println("num=" + num);
                }
            }
        }
    }

    public static void stopProcess(List<Process> processes) {
        processes.forEach(Process::interrupt);
    }

    public static void shutdown() {
        Schedule.stop = true;
    }

    static class Process extends Thread {
        /**
         * 资源 代表内存 -> 26 * 2
         */
        private static final char[] chars = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz".toCharArray();
        private static final AtomicInteger ip = new AtomicInteger();
        private static volatile boolean stop; // 控制所有的实例
        private static final AtomicInteger num = new AtomicInteger(2); // 最多打印几遍chars

        private int inTime;
        private int count = 1; // 当前线程打印的字符数
        private int endTime;
        private CountDownLatch latch;

        private volatile boolean done = false;

        public Process(int inTime, int count, int endTime) {
            this.inTime = inTime;
            this.count = count; // count必须是正整数
            this.endTime = endTime;
            setName("运行线程" + inTime);
        }

        public static void shutdown() {
            stop = true;
        }

        public Process setLatch(CountDownLatch latch) {
            this.latch = latch;
            return this;
        }

        public boolean isDone() {
            return done;
        }

        public Process setDone(boolean done) {
            this.done = done;
            return this;
        }

        public void run() {
            while (!isInterrupted()) {
                LockSupport.park();
                if (!stop) { // 没有停止，stop时直接丢弃
                    if (num.get() > 0) { // 资源足够
                        int i = 0;
                        while (i < count) {
                            i++;
                            System.out.println(getName() + "-" + chars[(ip.getAndIncrement()) % chars.length]);
                            if (ip.get() >= chars.length) {
                                num.decrementAndGet();
                                ip.set(0); // 重新计数
                            }
                        }
                    } else {
                        shutdown();
                    }
                }
                latch.countDown();
                setDone(true);
            }
        }

        public int getInTime() {
            return inTime;
        }

        public void setInTime(int inTime) {
            this.inTime = inTime;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getEndTime() {
            return endTime;
        }

        public void setEndTime(int endTime) {
            this.endTime = endTime;
        }
    }
}
