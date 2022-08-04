package org.pp.net.socket.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Iterator;

/**
 * 通道
 * buffer
 * 选择器
 * <p>
 * 通道注册多路选择器和事件
 * 通道读写buffer
 */
public class NioSocketServer {
    public static void main(String[] args) {
        try {
            ServerSocketChannel server = ServerSocketChannel.open();
            server.bind(new InetSocketAddress("localhost", 8080));
            server.configureBlocking(false);

            // 打开多路选择器
            Selector selector = Selector.open();
            // 通道注册选择器和事件
            server.register(selector, SelectionKey.OP_ACCEPT);

            while (true) {
                selector.select(); // 阻塞等待事件
                Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
                while (iter.hasNext()) {
                    SelectionKey selectionKey = iter.next();
                    iter.remove();
                    if (selectionKey.isAcceptable()) {
                        handleAccept(selectionKey, selector);
                    } else if (selectionKey.isReadable()) {
                        handleRead(selectionKey);
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void handleRead(SelectionKey key) {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        try {
            socketChannel.read(buffer); // 从通道中读取数据存入buffer
        } catch (IOException e) {
            e.printStackTrace();
        }
        buffer.flip(); // 将限制设置为当前位置，然后将该位置设置为零
        int limit = buffer.limit();
        byte[] realData = new byte[limit];
        buffer.get(realData);
        String str = new String(realData, StandardCharsets.UTF_8);
        System.out.println("接收到数据：" + str);

        ByteBuffer buffer1 = ByteBuffer.wrap("欢迎欢迎2".getBytes(StandardCharsets.UTF_8));
        try {
            socketChannel.write(buffer1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void handleAccept(SelectionKey key, Selector selector) {
        ServerSocketChannel server = (ServerSocketChannel) key.channel();
        try {
            SocketChannel socket = server.accept();
            socket.configureBlocking(false);
            socket.write(ByteBuffer.wrap("欢迎欢迎".getBytes(StandardCharsets.UTF_8)));

            socket.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
