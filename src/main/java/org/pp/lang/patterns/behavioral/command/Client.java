package org.pp.lang.patterns.behavioral.command;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * ************自强不息************
 *
 * @author 鹏鹏
 * @date 2022/12/4 8:32
 * ************厚德载物************
 **/
public class Client {

    private static final ExecutorService service = Executors.newFixedThreadPool(2);

    public static void main(String[] args) throws InterruptedException {
        // 发送请求
        service.submit(() -> {
            Command command = new RealRequestCommand();
            Request request = new Request(command);
            for (int i = 0; i < 10; i++) {
                request.intoQueue();
            }
        });
        TimeUnit.MILLISECONDS.sleep(10);
        // 处理请求
        service.submit(() -> Request.handleRequest());
        TimeUnit.SECONDS.sleep(2);
        service.shutdown();
    }
}
