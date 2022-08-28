package org.pp.net.rpc.reactor.core;

import java.io.IOException;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;

public class Acceptor implements Runnable {

    private final ServerSocketChannel serverSocketChannel;
    private final SelectorWrapper selectorWrapper;
    private final ExecutorService boss;
    private final ExecutorService service;

    public Acceptor(ServerSocketChannel serverSocketChannel, SelectorWrapper selectorWrapper, ExecutorService boss, ExecutorService service) {
        this.serverSocketChannel = serverSocketChannel;
        this.selectorWrapper = selectorWrapper;
        this.boss = boss;
        this.service = service;
    }

    public void startup() {
        try {
            SocketChannel socketChannel = serverSocketChannel.accept();
            System.out.println(socketChannel + "新客户端连接...");
            boss.submit(new AcceptorHandler(socketChannel, selectorWrapper, service));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        startup();
    }
}
