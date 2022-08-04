package org.pp.net.socket.nio;

import java.io.IOException;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;

public class Acceptor implements Runnable {

    Selector selector;
    ServerSocketChannel serverSocketChannel;
    ExecutorService worker;

    public Acceptor(Selector selector, ServerSocketChannel serverSocketChannel, ExecutorService worker) {
        this.selector = selector;
        this.serverSocketChannel = serverSocketChannel;
        this.worker = worker;
    }

    public void run() {
        try {
            SocketChannel socketChannel = serverSocketChannel.accept();
            socketChannel.configureBlocking(false);
            new Handler(selector, socketChannel, worker);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
