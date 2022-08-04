package org.pp.net.socket.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Reactor {

    Selector selector;
    ServerSocketChannel serverSocketChannel;
    ExecutorService boss = Executors.newFixedThreadPool(2);
    ExecutorService worker = Executors.newFixedThreadPool(2);

    public Reactor() {
        try {
            selector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.bind(new InetSocketAddress(8080));
            serverSocketChannel.configureBlocking(false);

            SelectionKey selectionKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            selectionKey.attach(new Acceptor(selector, serverSocketChannel, worker));
//            selectionKey.selector();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        try {
            selector.select();
            Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
            while (iter.hasNext()) {
                SelectionKey selectionKey = iter.next();
                dispatch(selectionKey);
                iter.remove();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void dispatch(SelectionKey selectionKey) {
        Acceptor acceptor = (Acceptor) selectionKey.attachment();
        boss.execute(acceptor);
    }
}
