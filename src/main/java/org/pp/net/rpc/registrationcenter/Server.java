package org.pp.net.rpc.registrationcenter;

import org.pp.net.rpc.reactor.core.Bootstrap;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 注册中心
 * 远程注册服务 -- 服务名:url地址列表
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
