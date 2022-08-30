package org.pp.net.rpc.reactor.core;

import org.pp.net.rpc.registrationcenter.connect.ConnectHandler;

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

    public AcceptorHandler() {

    }

    public AcceptorHandler(SocketChannel socketChannel, SelectorWrapper selectorWrapper, ExecutorService service) {
        this.socketChannel = socketChannel;
        this.selectorWrapper = selectorWrapper;
        this.selector = selectorWrapper.getCurrSelector();
        this.service = service;
    }

    public SocketChannel getSocketChannel() {
        return socketChannel;
    }

    public void setSocketChannel(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
    }

    public SelectorWrapper getSelectorWrapper() {
        return selectorWrapper;
    }

    public void setSelectorWrapper(SelectorWrapper selectorWrapper) {
        this.selectorWrapper = selectorWrapper;
    }

    public Selector getSelector() {
        return selector;
    }

    public void setSelector(Selector selector) {
        this.selector = selector;
    }

    public ExecutorService getService() {
        return service;
    }

    public void setService(ExecutorService service) {
        this.service = service;
    }

    public SelectionKey getKey() {
        return key;
    }

    public void setKey(SelectionKey key) {
        this.key = key;
    }

    public Acceptor getAcceptor() {
        return acceptor;
    }

    public void setAcceptor(Acceptor acceptor) {
        this.acceptor = acceptor;
    }

    public abstract void bindChildHandler();

    @Override
    public void run() {
        try {
            System.out.println("AcceptorHandler start");
            socketChannel.configureBlocking(false);
            key = socketChannel.register(selector, 0);
            ConnectHandler handler = new ConnectHandler(this);
            key.attach(handler);
            key.interestOps(SelectionKey.OP_READ);
            selector.wakeup();
            System.out.println("AcceptorHandler end");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
