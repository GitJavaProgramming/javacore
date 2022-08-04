package org.pp.net.socket.nio;

import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;

public class Handler implements Runnable {

    Selector selector;
    SocketChannel socketChannel;
    ExecutorService worker;

    public Handler(Selector selector, SocketChannel socketChannel, ExecutorService worker) {
        this.selector = selector;
        this.socketChannel = socketChannel;
        this.worker = worker;
    }

    @Override
    public void run() {
        try {
            socketChannel.register(selector, SelectionKey.OP_READ);
            worker.execute(() -> {
            });
        } catch (ClosedChannelException e) {
            e.printStackTrace();
        }
    }
}
