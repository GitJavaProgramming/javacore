package org.pp.concurrent.thread;

/**
 * 线程api
 * 中断
 */
public class ThreadApi {
    public static void main(String[] args) throws InterruptedException {
//        testInterrupt();
        getAllThread();
    }

    public static void getAllThread() {
        ThreadGroup group = Thread.currentThread().getThreadGroup();
        ThreadGroup topGroup = group;
        // 遍历线程组树，获取根线程组
        while (group != null) {
            topGroup = group;
            group = group.getParent();
        }
        // 激活的线程数再加一倍，防止枚举时有可能刚好有动态线程生成
        int slackSize = topGroup.activeCount() * 2;
        Thread[] slackThreads = new Thread[slackSize];
        // 获取根线程组下的所有线程，返回的actualSize便是最终的线程数
        int actualSize = topGroup.enumerate(slackThreads);
        Thread[] actualThreads = new Thread[actualSize];
        // 复制slackThreads中有效的值到actualThreads
        System.arraycopy(slackThreads, 0, actualThreads, 0, actualSize);
        System.out.println("Threads size is " + actualThreads.length);
        for (Thread thread : actualThreads) {
            System.out.println("Thread name : " + thread.getName());
        }

    }

    public static void testInterrupt() throws InterruptedException {
        Thread t = new Thread(() -> {
            System.out.println("run......");
            long i = 0;
            while (!Thread.currentThread().isInterrupted()/*测试线程是否被中断*/) { // 线程不中断就计数
                i++;
            }
            System.out.println("线程被中断：i=" + i);
            System.out.println(Long.MAX_VALUE);

            Thread.interrupted(); // 清除中断

            long j = i + 600;
            while (i < j && !Thread.currentThread().isInterrupted()) {
                try {
                    Thread.sleep(10); // 每次进入循环休眠10毫秒，每秒自加100次，自加到600大概6秒左右
                    i++;
                } catch (InterruptedException e) {
                    System.out.println("线程被中断：i=" + i);
                    Thread.interrupted(); // 清除中断
                }
            }
            System.out.println("运行结束：i=" + i);
        });
        t.start();
        for (int i = 0; i < 5; i++) {
            Thread.sleep(1000);
            t.interrupt(); // 中断线程t
        }
    }
}
