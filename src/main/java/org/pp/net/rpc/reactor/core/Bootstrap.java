package org.pp.net.rpc.reactor.core;

import org.pp.net.rpc.registrationcenter.Acceptor;
import org.pp.net.rpc.registrationcenter.AcceptorHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Bootstrap {

    private static final int N_CPU = Runtime.getRuntime().availableProcessors();
    private ExecutorService bossExecutor = Executors.newSingleThreadExecutor();
    private ExecutorService workExecutor = Executors.newFixedThreadPool(N_CPU);

    private SelectorWrapper selectorWrapper;
    private Selector[] selectors;
    private Selector mainSelector;
    private ServerSocketChannel serverSocketChannel;

    private Acceptor acceptor;

    public Bootstrap() {
        try {
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            selectorWrapper = configSelector(1);
            selectors = selectorWrapper.getSelectors();
            mainSelector = selectorWrapper.getSelectors()[0];
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Bootstrap bind(int port) {
        try {
            serverSocketChannel.bind(new InetSocketAddress(port), 1024);
            System.out.println("服务器监听中...");
            serverSocketChannel.register(mainSelector, SelectionKey.OP_ACCEPT, acceptor);
            poll(); // 轮询selector
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public Bootstrap initAcceptor(AcceptorHandler handler) throws IOException {
        Acceptor acceptor = new Acceptor(serverSocketChannel, selectorWrapper, bossExecutor, workExecutor);
        // 双向绑定
        handler.setAcceptor(acceptor);
        acceptor.setHandler(handler);
        return this;
    }

    private void poll() throws IOException {
        System.out.println("selectors len:" + selectors.length);
        while (true) {
            for (Selector selector : selectors) {
                int select = selector.select(); // 阻塞  水平触发
                if (select == 0) {
                    System.out.println("select 返回..."); // 关闭连接时返回多次
                    continue;
                } else {
//                    System.out.println("感兴趣事件数：" + select);
                }
                Set<SelectionKey> keySet = selector.selectedKeys();
                Iterator<SelectionKey> iterator = keySet.iterator();
                while (iterator.hasNext()) {
                    SelectionKey next = iterator.next();
                    dispatch(next);
                    iterator.remove();
                }
            }
        }
    }

    private void dispatch(SelectionKey selectionKey) {
        Runnable attachment = (Runnable) selectionKey.attachment();
        if (attachment != null) {
            attachment.run();
        }
    }

    private SelectorWrapper configSelector(int count) {
        SelectorWrapper selectorWrapper = SelectorWrapper.getInstance(count);
        return selectorWrapper;
    }

    public Acceptor getAcceptor() {
        return acceptor;
    }

    public SelectorWrapper getSelectorWrapper() {
        return selectorWrapper;
    }

    public ServerSocketChannel getServerSocketChannel() {
        return serverSocketChannel;
    }

    public ExecutorService getBoss() {
        return bossExecutor;
    }

    public ExecutorService getWorker() {
        return workExecutor;
    }

    public Bootstrap boos(ExecutorService boss) {
        this.bossExecutor = boss;
        return this;
    }

    public Bootstrap worker(ExecutorService workExecutor) {
        this.workExecutor = workExecutor;
        return this;
    }
}
