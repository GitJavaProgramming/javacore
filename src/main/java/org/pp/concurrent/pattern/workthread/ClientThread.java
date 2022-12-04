package org.pp.concurrent.pattern.workthread;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class ClientThread extends Thread {
    private final Channel channel;
    private static final Random random = new Random();

    public ClientThread(String name, Channel channel) {
        super(name);
        this.channel = channel;
    }

    @Override
    public void run() {
        int i = 0;
        while (true) {
            Request request = new Request(getName(), i);
            channel.putRequest(request);
            try {
                TimeUnit.MILLISECONDS.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
