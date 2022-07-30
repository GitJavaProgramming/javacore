package org.pp.concurrent.synchronize;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class DeadLockTest {
    static final ReentrantLock a = new ReentrantLock();
    static final ReentrantLock b = new ReentrantLock();

    public static void main(String[] args) {
        Thread A = new Thread(() -> invokeA(Thread.currentThread().getName()), "DeadLockThread-A");
        Thread B = new Thread(() -> invokeB(Thread.currentThread().getName()), "DeadLockThread-B");
        A.start();
        B.start();
        new Timer("timer", true/*后台线程不影响程序退出*/).schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("timer run.");
                A.interrupt();
                B.interrupt(); // A中断后B能不能获取到a锁？
            }
        }, 2000);
    }

    public static void invokeB(String name) {
        System.out.println("B.");
        try {
            b.lockInterruptibly();
            System.out.println(name + " ob Locked");
            TimeUnit.SECONDS.sleep(1);
            try {
                a.lockInterruptibly();
                System.out.println(name + " oa Locked");
            } catch (InterruptedException e) {
                System.out.println(name + " oa Interrupted 捕捉到中断异常.");
            } finally {
                if (a.isHeldByCurrentThread()) {
                    a.unlock();
                }
            }
        } catch (InterruptedException e1) {
            System.out.println(name + " ob Interrupted 捕捉到中断异常.");
        } finally {
            b.unlock();
        }
    }

    public static void invokeA(String name) {
        System.out.println("A.");
        try {
            a.lockInterruptibly();
            System.out.println(name + " oa Locked");
            TimeUnit.SECONDS.sleep(1);
            try {
                b.lockInterruptibly();
                System.out.println(name + " ob Locked");
            } catch (InterruptedException e) {
                System.out.println(name + " ob Interrupted 捕捉到中断异常.");
            } finally {
                if (b.isHeldByCurrentThread()) {
                    b.unlock();
                }
            }
        } catch (InterruptedException e1) {
            System.out.println(name + " oa Interrupted 捕捉到中断异常.");
        } finally {
            a.unlock();
        }
    }
}
