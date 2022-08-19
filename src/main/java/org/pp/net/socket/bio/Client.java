package org.pp.net.socket.bio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Client {
    private static final Socket socket = new Socket();

    public static void main(String[] args) {
        try {
            socket.connect(new InetSocketAddress("localhost", 8080));
            if (socket.isConnected() && !socket.isClosed()) {
                System.out.println("socket连接中：" + socket);
            }
            process(socket.getInputStream(), socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    static void process(InputStream in, OutputStream out) throws IOException {
        System.out.println("向服务器发送数据...");
        out.write("鹏鹏".getBytes(StandardCharsets.UTF_8));
        out.flush();

        byte[] bytes = new byte[1024];
        System.out.println("阻塞读数据...");
        in.read(bytes);
        String str = new String(bytes, StandardCharsets.UTF_8);
        System.out.println(str.trim());
    }
}
