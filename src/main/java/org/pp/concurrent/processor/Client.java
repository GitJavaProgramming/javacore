package org.pp.concurrent.processor;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

/**
 * 线程队列
 * 任务队列--公共队列与线程独有队列
 */
public class Client {

    private static int nThreads = 10;

    public static void main(String[] args) {
//        testThread();
//        testProcessor();
//        testProcessor2();
//        testProcessor3();
        testExecutor();
    }

    private static void testExecutor() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        AtomicInteger t = new AtomicInteger();
        service.scheduleAtFixedRate(() -> {
            for (int i = 0; i < 10; i++) {
                executor.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + "--" + t.getAndDecrement() + "hahaha");
//                    for (int x = 0; x < Integer.MAX_VALUE; x++) { }
                });
            }
            service.shutdown(); // 这里不能这么用吧？？会导致executor无法关闭
        }, 0, 5, TimeUnit.SECONDS);
        System.out.println("t==" + t);
        executor.shutdown(); // 这是bug吗?为什么不能关闭了呢？
    }

    private static void testProcessor3() {
        MyExecutor executor = new MyExecutor(4/*最多N个线程*/);
        MyExecutor service = new MyExecutor(1);
        System.out.println("executor=" + executor.hashCode());
        System.out.println("service=" + service.hashCode());
        AtomicInteger t = new AtomicInteger();
        service.execute(() -> {
            for (int i = 0; i < 10; i++) {
                executor.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + "--" + t.getAndDecrement() + "hahaha");
                });
            }
//            service.shutdown();
        });
        try {
            TimeUnit.SECONDS.sleep(1); // 等任务执行
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executor.shutdown();
        service.shutdown();
    }

    private static void testProcessor2() {
        List<TaskThread> list = Stream.of(new TaskThread(1),
                new TaskThread(2),
                new TaskThread(3),
                new TaskThread(4)).toList();
        list.forEach(taskThread -> {
            for (int i = 0; i < 10; i++) {
                int finalI = i;
                taskThread.submit(() -> System.out.println(taskThread.getName() + "--" + finalI + "hahaha"));
            }
        });
        list.forEach(TaskThread::shutdownGracefully);
    }

    private static void testThread() {
        TaskThread taskThread = new TaskThread(1);
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            taskThread.submit(() -> System.out.println(finalI + "hahaha"));
        }
        taskThread.shutdownGracefully();
    }

    private static void testProcessor() {
        Processor schedule2 = new Processor();
        schedule2.schedule(schedule2.ready(nThreads)/*准备的线程队列*/);
    }
}
