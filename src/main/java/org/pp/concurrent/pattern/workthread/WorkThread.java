package org.pp.concurrent.pattern.workthread;

/**
 * 等到工作来，来了就工作
 * 两组线程：工作线程预先启动等待工作到来，生产者线程生产
 */
public class WorkThread extends Thread {
    private final Channel channel;

    public WorkThread(String name, Channel channel) {
        super(name);
        this.channel = channel;
    }

    @Override
    public void run() {
        while (true) {
            Request request = channel.takeRequest();
            request.execute();
        }
    }
}
