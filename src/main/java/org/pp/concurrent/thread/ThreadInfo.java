package org.pp.concurrent.thread;

import java.util.Arrays;

/**
 * Java线程属性中最重要的一个信息就是线程状态
 * 该程序测试线程的状态
 */
public class ThreadInfo {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Java线程全部状态：" + Arrays.toString(Thread.State.values()));
        Thread thread = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "线程start...");
            Thread[] ts = new Thread[2]; // main、Monitor Ctrl-Break、Thread-0
            Thread.currentThread().getThreadGroup().enumerate(ts);
            Arrays.stream(ts).forEach(ThreadInfo::printThreadInfo);
            System.out.println(Thread.currentThread().getName() + "线程end...");
        });
        System.out.println(thread.getName() + "线程状态：" + thread.getState()); // NEW
        System.out.println("*************************************************************");
        thread.setDaemon(true);
        thread.start();
        // 当前线程休眠，暂停当前线程活动。线程不会失去任何监视器的所有权？到底是谁让main休眠？
        // public static native void sleep(long millis) throws InterruptedException;
        Thread.sleep(3000);
        System.out.println("线程状态：" + thread.getState()); // TERMINATED
        System.out.println("*************************************************************");
    }

    /**
     * JAVA线程API属性：
     * threadGroup、id、name、state、priority、daemon
     **/
    private static void printThreadInfo(Thread t) {
        StringBuilder sb = new StringBuilder();
        sb.append("线程组：").append(t.getThreadGroup()).append("\n")
                .append("线程ID:").append(t.getId()).append("\n")
                .append("name:").append(t.getName()).append("\n")
                .append("state:").append(t.getState()).append("\n")
                .append("priority:").append(t.getPriority()).append("\n")
                .append("daemon:").append(t.isDaemon()).append("\n");
        System.out.println(sb);
    }
}
