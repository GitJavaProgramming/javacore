package org.pp.net.rpc.registrationcenter.connect;

import org.pp.net.rpc.reactor.core.AbstractIOHandler;
import org.pp.net.rpc.reactor.event.ConnectionClosedEvent;
import org.pp.net.rpc.reactor.listener.ConnectListener;
import org.pp.net.rpc.registrationcenter.AcceptorHandler;
import org.pp.net.rpc.registry.MethodWrapper;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class ConnectHandler extends AbstractIOHandler {

    public ConnectHandler(AcceptorHandler acceptorHandler) {
        this.acceptorHandler = acceptorHandler;
    }

    public ConnectHandler(SocketChannel channel, SelectionKey key, ExecutorService service) {
        this.channel = channel;
        this.key = key;
        this.service = service;
        this.listener = new ConnectListener(channel, key.selector(), this);
        System.out.println("生成IOHandler实例====" + hashCode());
    }

    @Override
    public void send() {

    }

    public Future read() {
        Future<?> future = service.submit(() -> {
            if (channel.isOpen()) {
                ByteBuffer input = ByteBuffer.allocate(1024);
                // 处于非阻塞模式的套接字通道不能读取比套接字输入缓冲区中立即可用的字节数更多的字节数。
                // java nio 水平触发也叫条件触发 socket缓冲区中有数据可读就触发读事件
                int len = 0;
                try {
                    len = channel.read(input);
                } catch (IOException e) {
                    // 当客户端主动连接断开时，为了让服务器知道断开了连接，会产生OP_READ事件。
                    listener.onConnectionException(new ConnectionClosedEvent(this), e);
                }
                StringBuilder sb = new StringBuilder();
                // 有len个可用字节 read可能返回0 读取字节数可能超过input容量
                if (len > 0) {
                    input.flip();
                    byte[] bytes = new byte[input.remaining()];
                    input.get(bytes);
                    // 将字节数组转换成java对象
                    MethodWrapper wrapper = new MethodWrapper();
                }
                if (len == -1) { // 当客户端主动连接断开时，为了让服务器知道断开了连接，会产生OP_READ事件。所以需要判断读取长度，当读到-1时，关闭channel。
                    listener.onConnectionClosed(new ConnectionClosedEvent(this));
                    return;
                }
            } else {
//                        System.out.println(key + "通道关闭状态...");
            }
        });
        return future;
    }
}
