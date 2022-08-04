package org.pp.concurrent.pattern.readwritelock;

import java.util.stream.IntStream;

public class Client {
    private static final String text = "你好中国";

    public static void main(String[] args) {
        final ShareData shareData = new ShareData(50);
        // 10个写线程
        IntStream.range(0, 10).mapToObj(i -> new Thread(() -> {
            for (int index = 0; index < text.length(); index++) {
                char c = text.charAt(index);
                try {
                    shareData.write(c);
                    System.out.println(Thread.currentThread().getName() + "写入数据：" + c);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        })).forEach(Thread::start);
        // 2个都线程 读取
        IntStream.range(0, 2).mapToObj(i -> new Thread(() -> {
            for (int index = 0; index < text.length(); index++) {
                try {
                    String str = new String(shareData.read());
                    System.out.println(Thread.currentThread().getName() + "读取数据：" + str);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        })).forEach(Thread::start);
    }
}
