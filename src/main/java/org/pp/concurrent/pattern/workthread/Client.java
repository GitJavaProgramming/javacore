package org.pp.concurrent.pattern.workthread;

public class Client {
    public static void main(String[] args) {
        Channel channel = new Channel(5);
        channel.startWorkers();
        new ClientThread("11", channel/*共享*/).start();
        new ClientThread("22", channel).start();
        new ClientThread("33", channel).start();
    }
}
