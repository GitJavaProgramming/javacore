package org.pp.net.socket.bio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class SocketHandler implements Runnable {

    private final Socket socket;

    private final Thread t;

    public SocketHandler(Socket socket) {
        this.socket = socket;
        t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        try {
            process(socket.getInputStream(), socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void process(InputStream in, OutputStream out) throws IOException {
        byte[] bytes = new byte[1024];
        System.out.println(socket + "阻塞读数据...");
        in.read(bytes);
        String str = new String(bytes, StandardCharsets.UTF_8);
        System.out.println("接收数据：" + str.trim());

        str = "服务器返回：你好";
        out.write(str.getBytes(StandardCharsets.UTF_8));
        out.flush();
        System.out.println(socket + "写数据...");
    }
}
