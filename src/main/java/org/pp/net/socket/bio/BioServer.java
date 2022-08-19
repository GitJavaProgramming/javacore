package org.pp.net.socket.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class BioServer {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(9090);
        while(true) {
            Socket socket = server.accept();
            System.out.println(socket.getRemoteSocketAddress() + "连接来到");

            byte[] data = new byte[1024];
            socket.getInputStream().read(data);
            System.out.println("接收数据" + new String(data));

            socket.getOutputStream().write("hello".getBytes());
            socket.close();
        }
    }
}
