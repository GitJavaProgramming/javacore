package org.pp.net.rpc.registrationcenter;

import org.pp.net.rpc.reactor.core.Bootstrap;
import org.pp.net.rpc.registry.MethodWrapper;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 注册中心
 * 注册服务 -- 应用名:ip
 */
public class Server {
    private static final int N_CPU = Runtime.getRuntime().availableProcessors();

    public static void main(String[] args) {
        ExecutorService bossExecutor = Executors.newSingleThreadExecutor();
        ExecutorService workExecutor = Executors.newFixedThreadPool(N_CPU);
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.boos(bossExecutor)
                .worker(workExecutor)
                .bind(9090/*注册中心监听端口号*/);
    }
}
