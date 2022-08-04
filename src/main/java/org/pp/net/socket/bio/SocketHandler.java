package org.pp.net.socket.bio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class SocketHandler implements Runnable {

    private Socket socket;

    private Thread t;

    public SocketHandler(Socket socket) {
        this.socket = socket;
        t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        try (InputStream in = socket.getInputStream(); OutputStream out = socket.getOutputStream()) {
            byte[] bytes = in.readAllBytes();
            String str = new String(bytes, StandardCharsets.UTF_8);
            System.out.println("接收数据：" + str);

            str = "服务器返回：你好";
            out.write(str.getBytes(StandardCharsets.UTF_8));
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
