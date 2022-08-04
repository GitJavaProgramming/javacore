package org.pp.net.socket.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Iterator;

public class NioClient {
    public static void main(String[] args) {
        SocketChannel socketChannel = null;
        try {
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            socketChannel.connect(new InetSocketAddress("localhost", 8080));

            Selector selector = Selector.open();
            socketChannel.register(selector, SelectionKey.OP_CONNECT);

            while (true) {
                selector.select();
                Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
                while (iter.hasNext()) {
                    SelectionKey selectionKey = iter.next();
                    iter.remove();
                    if (selectionKey.isConnectable()) {
                        handleConnect(selectionKey, selector);
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
            socketChannel.read(buffer);
            buffer.flip();
            int limit = buffer.limit();
            byte[] realData = new byte[limit];
            buffer.get(realData);
            System.out.println(Arrays.toString(realData));
            System.out.println("读取服务器发送的数据：" + new String(realData, StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void handleConnect(SelectionKey key, Selector selector) {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        if(socketChannel.isConnectionPending()) {
            try {
                socketChannel.finishConnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            socketChannel.write(ByteBuffer.wrap("请求连接.".getBytes(StandardCharsets.UTF_8)));
            socketChannel.register(selector, SelectionKey.OP_READ);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
