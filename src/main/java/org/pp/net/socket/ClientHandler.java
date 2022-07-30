package org.pp.net.socket;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private final Socket client;
    private final ServerOptions options;

    public ClientHandler(Socket client) {
        this.client = client;
        this.options = new ServerOptions();
    }

    public ClientHandler(ServerOptions options, Socket client) {
        this.client = client;
        this.options = options;
    }


    @Override
    public void run() {
        try (InputStream in = client.getInputStream(); OutputStream out = client.getOutputStream()) {
            OutputStreamWriter writer = new OutputStreamWriter(new BufferedOutputStream(out));
            writer.write("echo:" + "你哈。。。。");
            writer.flush();
            // 流的本质就是字节数组 缓存区也是字节数组只是操作方式的区别
            System.out.println("in==" + in.hashCode());
            System.out.println("out==" + out.hashCode());
            System.out.println(in.available());
            byte[] bytes = in.readAllBytes();
            String str = new String(bytes);
            System.out.println(str);
        } catch (IOException e) {
            System.out.println(client + "closed.");
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
