package org.pp.lang.patterns.behavioral.command;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * ************自强不息************
 * <p>
 * 将请求封装成一个对象，然后可以对请求排队、记录请求日志以及支持可撤销的操作
 * 真实请求由Command代理
 *
 * @author 鹏鹏
 * @date 2022/12/4 8:04
 * ************厚德载物************
 **/
public class Request {
    private static final BlockingQueue<Command> commandQueue = new ArrayBlockingQueue<>(10);
    private final Command command;

    public Request(Command command) {
        this.command = command;
    }

    public static void handleRequest() {
        synchronized (commandQueue) {
            while (!commandQueue.isEmpty()) {
                Command poll = commandQueue.poll();
                poll.execute();
            }
        }
    }

    public void intoQueue() {
        synchronized (commandQueue) {
            commandQueue.add(command);
        }
    }

    public void log() {
        System.out.println(command.name() + "记录请求日志...");
        command.execute();
    }

}
