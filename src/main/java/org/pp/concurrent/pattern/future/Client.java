package org.pp.concurrent.pattern.future;

import java.util.concurrent.TimeUnit;

public class Client {
    public static void main(String[] args) {
        test02();
    }

    public static void test01() {
        FutureService<Void, Void> service = FutureService.newService();
        Future<?> future = service.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("完成");
        });
        try {
            future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void test02() {
        FutureService<String, Integer> service = FutureService.newService();
        Future<Integer> future = service.submit(input -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return input.length();
        }, "hello");
        try {
            System.out.println(future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void test03() {
        FutureService<String, Integer> service = FutureService.newService();
        Future<Integer> future = service.submit(input -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return input.length();
        }, "hello", System.out::println);
    }
}
