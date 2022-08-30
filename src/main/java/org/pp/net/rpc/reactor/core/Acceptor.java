package org.pp.net.rpc.reactor.core;

import org.pp.net.rpc.registrationcenter.connect.ConnectHandler;

import java.io.IOException;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;

public class Acceptor implements Runnable {

    private final ServerSocketChannel serverSocketChannel;
    private final SelectorWrapper selectorWrapper;
    private final ExecutorService boss;
    private final ExecutorService service;
    private SocketChannel socketChannel;

    public Acceptor(ServerSocketChannel serverSocketChannel, SelectorWrapper selectorWrapper, ExecutorService boss, ExecutorService service) {
        this.serverSocketChannel = serverSocketChannel;
        this.selectorWrapper = selectorWrapper;
        this.boss = boss;
        this.service = service;
    }

    public void startup() {
        try {
            socketChannel = serverSocketChannel.accept();
            System.out.println(socketChannel + "新客户端连接...");
            AcceptorHandler acceptorHandler = new AcceptorHandler() {
                @Override
                public void bindChildHandler() {
//                    setIOHandler(new ConnectHandler());
                }
            };
            setAcceptorHandler(acceptorHandler);
            acceptorHandler.setAcceptor(this);
            boss.submit(acceptorHandler); // 应该维护责任链
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        startup();
    }

    public void setAcceptorHandler(AcceptorHandler acceptorHandler) {
        acceptorHandler.setSocketChannel(socketChannel);
        acceptorHandler.setSelectorWrapper(selectorWrapper);
        acceptorHandler.setService(service);
        acceptorHandler.setSelector(selectorWrapper.getCurrSelector());
    }
}
