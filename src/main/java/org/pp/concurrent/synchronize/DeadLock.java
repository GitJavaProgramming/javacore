package org.pp.concurrent.synchronize;

import java.util.concurrent.TimeUnit;

/**
 * ************自强不息************
 *
 * @author 鹏鹏
 * @date 2022/11/21 9:18
 * 加锁顺序不一样 锁占用时间长 导致死锁
 * ************厚德载物************
 **/
public class DeadLock {
    private static final Object a = new Object();
    private static final Object b = new Object();

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new A());
        Thread t2 = new Thread(new B());
        t1.start();
        t2.start();
//        // 可中断方法异常处理
////        TimeUnit.SECONDS.sleep(1);
//        TimeUnit.SECONDS.sleep(3); // 超过A、B线程sleep时限2秒则中断失效，必须在sleep期间中断才可抛出中断异常
//        t1.interrupt();
//        t2.interrupt();
    }

    static class A implements Runnable {

        @Override
        public void run() {
            try {
                synchronized (a) {
                    TimeUnit.SECONDS.sleep(2);
                    System.out.println("A.a 请求 b");
                    // 1秒后释放锁
                    TimeUnit.SECONDS.sleep(1);
                    a.wait();
//                    a.wait(1);
                    synchronized (b) {
                        System.out.println("A.b");
                    }
                }
            } catch (InterruptedException e) {
                System.out.println("A Interrupted");
            }
        }
    }

    static class B implements Runnable {
        @Override
        public void run() {
            try {
                synchronized (b) {
                    TimeUnit.SECONDS.sleep(2);
                    System.out.println("B.b 请求 a");
                    synchronized (a) {
                        System.out.println("B.a");
                        a.notify(); // 唤醒在监视器a上等待的线程 去争用cpu
                    }
                }
            } catch (InterruptedException e) {
                System.out.println("B Interrupted");
            }
        }
    }
}
