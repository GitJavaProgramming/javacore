package org.pp.cn.hutool.socket;

import cn.hutool.socket.nio.NioClient;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class NioClientTest {
    public static void main(String[] args) {
        NioClient client = new NioClient("localhost", 9090);
        client.setChannelHandler(socketChannel -> {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            buffer.put("你好".getBytes(StandardCharsets.UTF_8));
            buffer.flip();
            socketChannel.write(buffer);
        });
        client.listen();
    }
}
