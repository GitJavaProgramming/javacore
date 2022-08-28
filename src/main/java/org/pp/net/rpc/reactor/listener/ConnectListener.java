package org.pp.net.rpc.reactor.listener;

import org.pp.net.rpc.reactor.core.Handler;
import org.pp.net.rpc.reactor.event.Event;

import java.io.IOException;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class ConnectListener implements Listener {
    private SocketChannel channel;
    private Handler handler;
    private Selector selector;

    public ConnectListener(SocketChannel channel, Selector selector, Handler handler) {
        this.channel = channel;
        this.selector = selector;
        this.handler = handler;
    }

    /**
     * 客户端socket关闭 read抛出SocketException
     */
    public void onConnectionClosed(Event event) {
        try {
            synchronized (channel) {
                if (channel.isOpen()) {
                    System.out.println(channel + "连接关闭 ");
                    channel.close();
                }
            }
        } catch (IOException e1) {
            handler.connectCloseException();
        }
    }

    /**
     * 关闭客户端进程  连接异常
     */
    public void onConnectionException(Event event, Exception e) {
        try {
            synchronized (channel) {
                if (channel.isOpen()) {
                    System.out.println(channel + "连接异常 " + e.getClass().getSimpleName());
                    channel.close();
                }
            }
        } catch (IOException e1) {
            handler.connectCloseException();
        }
    }
}
