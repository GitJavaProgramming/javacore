package org.pp.net.socket;


import org.pp.concurrent.executor.ThreadPool;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

public class BIOServer {
    private static final int N_CPU = Runtime.getRuntime().availableProcessors();
    private static final ThreadPool threadPool = new ThreadPool(2, N_CPU);

    private final ServerOptions options = new ServerOptions();

    public static void main(String[] args) throws IOException {
        final BIOServer server = new BIOServer();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("timer run...");
                server.stop();
            }
        }, 10000);
        server.startup();
    }

    private void startup() throws IOException {
        ServerSocket server = new ServerSocket(8080, 2);
        while (!options.canStop()) {
            Socket client = server.accept();
            threadPool.submit(new ClientHandler(options, client));
        }
//        threadPool.shutdown();
    }

    private void stop() {
//        stop = true;
//        threadPool.shutdown();
    }
}
