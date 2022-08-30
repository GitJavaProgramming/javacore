package org.pp.net.rpc.registrationcenter;

import org.pp.net.rpc.reactor.core.AbstractIOHandler;
import org.pp.net.rpc.reactor.core.Handler;
import org.pp.net.rpc.reactor.core.SelectorWrapper;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;

public abstract class AcceptorHandler implements Handler {

    private SocketChannel socketChannel;
    private SelectorWrapper selectorWrapper;
    private Selector selector;
    private ExecutorService service;
    private SelectionKey key;

    private Acceptor acceptor;
    private AbstractIOHandler handler;

    public AcceptorHandler() {

    }

    public AcceptorHandler(SocketChannel socketChannel, SelectorWrapper selectorWrapper, ExecutorService service) {
        this.socketChannel = socketChannel;
        this.selectorWrapper = selectorWrapper;
        this.selector = selectorWrapper.getCurrSelector();
        this.service = service;
    }

    public void setSocketChannel(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
    }

    public void setSelectorWrapper(SelectorWrapper selectorWrapper) {
        this.selectorWrapper = selectorWrapper;
    }

    public void setService(ExecutorService service) {
        this.service = service;
    }

    public void setIOHandler(AbstractIOHandler handler) {
        this.handler = handler;
        handler.setAcceptorHandler(this);
    }

    public void setAcceptor(Acceptor acceptor) {
        this.acceptor = acceptor;
    }

    public abstract void setHandler();

    @Override
    public void run() {
        try {
            socketChannel.configureBlocking(false);
            key = socketChannel.register(selector, 0);
            key.attach(/*new ConnectHandler(socketChannel, key, service)*/handler);
            key.interestOps(SelectionKey.OP_READ);
            selector.wakeup();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
