package org.pp.cn.hutool.socket;

import cn.hutool.socket.nio.NioServer;

import java.nio.ByteBuffer;

public class NioServerTest {
    public static void main(String[] args) {
        NioServer server = new NioServer(9090);
        server.setChannelHandler(socketChannel -> {
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            socketChannel.read(byteBuffer);
            byteBuffer.flip();
            byte[] bytes = new byte[byteBuffer.remaining()];
            byteBuffer.get(bytes);
            System.out.println("nihao=" + new String(bytes));
        });
        server.start();
    }
}
