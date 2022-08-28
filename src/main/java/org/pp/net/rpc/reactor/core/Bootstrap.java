package org.pp.net.rpc.reactor.core;

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

    private static final ExecutorService bossExecutor = Executors.newSingleThreadExecutor();
    private static final int N_CPU = Runtime.getRuntime().availableProcessors();
    private static final ExecutorService workExecutor = Executors.newFixedThreadPool(N_CPU);

    private SelectorWrapper selectorWrapper;
    private Selector[] selectors;
    private Selector mainSelector;
    private ServerSocketChannel serverSocketChannel;

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
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public void init() throws IOException {

        Acceptor acceptor = new Acceptor(serverSocketChannel, selectorWrapper, bossExecutor, workExecutor);

        serverSocketChannel.register(mainSelector, SelectionKey.OP_ACCEPT, acceptor);

        poll(); // 轮询selector
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
        attachment.run();
    }

    private SelectorWrapper configSelector(int count) {
        SelectorWrapper selectorWrapper = SelectorWrapper.getInstance(count);
        return selectorWrapper;
    }
}
