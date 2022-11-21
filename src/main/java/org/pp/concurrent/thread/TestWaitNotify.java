package org.pp.concurrent.thread;

/**
 * ************自强不息************
 *
 * @author 鹏鹏
 * @date 2022/11/20 10:00
 * ************厚德载物************
 **/
public class TestWaitNotify {
    private static class Shared {
        private int age;
    }

    class T1 implements Runnable {

        private Shared shared;

        public T1(Shared shared) {
            this.shared = shared;
        }

        @Override
        public void run() {
            synchronized (shared) {
                shared.notify();
            }
        }
    }

    class T2 implements Runnable {

        private Shared shared;

        public T2(Shared shared) {
            this.shared = shared;
        }

        @Override
        public void run() {
            try {
                synchronized (shared) {
                    shared.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
