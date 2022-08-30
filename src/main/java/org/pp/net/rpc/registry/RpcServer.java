package org.pp.net.rpc.registry;

import org.pp.net.rpc.reactor.core.Bootstrap;
import org.pp.net.rpc.registrationcenter.AcceptorHandler;
import org.pp.net.rpc.registrationcenter.connect.ConnectHandler;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RpcServer {

    private static final int N_CPU = Runtime.getRuntime().availableProcessors();

    public void start(int port) {
        ExecutorService bossExecutor = Executors.newSingleThreadExecutor();
        ExecutorService workExecutor = Executors.newFixedThreadPool(N_CPU);
        Bootstrap bootstrap = new Bootstrap();
        try {
            bootstrap.boos(bossExecutor)
                    .worker(workExecutor)
                    .initAcceptor(new AcceptorHandler() {
                        @Override
                        public void setHandler() {
                            setIOHandler(/*与注册中心不同IOHandler，这里没实现使用同一个*/new ConnectHandler());
                        }
                    })
                    .bind(port);
        } catch (IOException e) {
            System.out.println("异常：" + e.getMessage());
        }
    }
}
