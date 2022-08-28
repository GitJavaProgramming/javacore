package org.pp.net.rpc.reactor.core;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;

public class AcceptorHandler implements Runnable {

    private final SocketChannel socketChannel;
    private final SelectorWrapper selectorWrapper;
    private final Selector selector;
    private final ExecutorService service;
    private SelectionKey key;

    public AcceptorHandler(SocketChannel socketChannel, SelectorWrapper selectorWrapper, ExecutorService service) {
        this.socketChannel = socketChannel;
        this.selectorWrapper = selectorWrapper;
        this.selector = selectorWrapper.getCurrSelector();
        this.service = service;
    }

    @Override
    public void run() {
        try {
            socketChannel.configureBlocking(false);
            key = socketChannel.register(selector, 0);
            key.attach(new IOHandler(socketChannel, key, service));
            key.interestOps(SelectionKey.OP_READ);
            selector.wakeup();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
