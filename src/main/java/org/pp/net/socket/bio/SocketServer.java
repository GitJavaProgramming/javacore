package org.pp.net.socket.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {

    public static void main(String[] args) throws IOException {
        Server server = new Server("服务器");
    }

    static class Server implements Runnable {

        private final String name;

        private final Thread t;

        private boolean stop = false;

        public Server(String name) {
            this.name = name;
            t = new Thread(this, name);
            t.start();
        }

        @Override
        public void run() {

            try (ServerSocket server = new ServerSocket(8080, 2)) {
                System.out.println("等待客户端连接");
                while (!stop) {
                    Socket client = server.accept();
                    System.out.println("接收到客户端连接：" + client);
                    new SocketHandler(client);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void stop() {
            stop = true;
        }
    }
}
