package org.pp.concurrent.synchronize;

/**
 * 等待通知机制，一个线程修改了变量值，其他线程可以感知
 * 线程api join
 */
public class WaitNotify {

    private static final Resource resource = new Resource();

    public static void main(String[] args) throws InterruptedException {
        WaitNotify waitNotify = new WaitNotify();
        waitNotify.test();
//        waitNotify.test2();
    }

    /**
     * 测试中断，c中状态改变时中断a的等待状态
     */
    public void test2() {
        A a = new A("Thread-A");
        C c = new C("Thread-C");
        a.start();
        c.start();
        while (true) {
            if (c.isDone()) {
                System.out.println("a wait... state=" + a.getState()); // TIMED_WAITING
                a.interrupt();
                break;
            }
        }
    }

    /**
     * 测试wait notify以及thread.join
     */
    public void test() {
        A a = new A("Thread-A");
        B b = new B("Thread-B");

        long t1 = System.currentTimeMillis();
        a.start();
        b.start();
        long t2 = System.currentTimeMillis();
        System.out.println("start end : " + (t2 - t1) + "ms");
        // main线程等待a、b线程运行结束，并不保证线程运行顺序
        try {
            a.join();
            b.join();
        } catch (InterruptedException e) {
            System.out.println("中断...");
        }
        long t3 = System.currentTimeMillis();
        System.out.println("run end : " + (t3 - t2) + "ms");
    }

    /**
     * 资源
     */
    static class Resource {
        private volatile boolean flag = true;
    }

    static class A extends Thread {

        private final String name;

        A(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            schedule(resource);
        }

        private void schedule(Resource resource) {
            synchronized (resource) {
                resource.flag = false;
                try {
                    System.out.println(name + "释放监视器锁 wait.");
                    resource.wait(3000); // 最多等待3s
                } catch (InterruptedException e) {
                    Thread.interrupted();
                    System.out.println("忽略中断");
                }
                System.out.println(name + "获得监视器锁 run here.");
            }
        }
    }

    static class B extends Thread {

        private final String name;

        B(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            schedule(resource);
        }

        private void schedule(Resource resource) {
            synchronized (resource) {
                while (true) {
                    if (!resource.flag) {
                        System.out.println("resource flag changed.");
                        break;
                    }
                }
                System.out.println(name + " notify.");
                resource.notify();
            }
        }
    }

    static class C extends Thread {

        private final String name;
        private volatile boolean done = false;

        C(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            schedule(resource);
        }

        private void schedule(Resource resource) {
            synchronized (resource) {
                while (true) {
                    if (!resource.flag) {
                        done = true;
                        System.out.println("c done.");
                        break;
                    }
                }
            }
        }

        public boolean isDone() {
            return done;
        }
    }
}
