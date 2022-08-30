package org.pp.net.rpc.registry;

import org.pp.net.rpc.reactor.core.Bootstrap;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RpcServer {

    private static final int N_CPU = Runtime.getRuntime().availableProcessors();

    public void start(int port) {
        ExecutorService bossExecutor = Executors.newSingleThreadExecutor();
        ExecutorService workExecutor = Executors.newFixedThreadPool(N_CPU);
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.boos(bossExecutor)
                .worker(workExecutor)
                .bind(port);
    }
}
