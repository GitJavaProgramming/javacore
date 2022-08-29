package org.pp.net.rpc.registrationcenter;

import org.pp.net.rpc.reactor.core.Bootstrap;
import org.pp.net.rpc.registrationcenter.connect.ConnectHandler;
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

    private final Map<Class, Set<MethodWrapper>> cache = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        ExecutorService bossExecutor = Executors.newSingleThreadExecutor();
        ExecutorService workExecutor = Executors.newFixedThreadPool(N_CPU);
        Bootstrap bootstrap = new Bootstrap();
        try {
            bootstrap.boos(bossExecutor)
                    .worker(workExecutor)
                    .setHandler(new AcceptorHandler(bootstrap.getAcceptor()) {
                        @Override
                        public void setHandler() {
                            setIOHandler(new ConnectHandler(this));
                        }
                    }).initAcceptor().bind(9090/*注册中心监听端口号*/);
        } catch (IOException e) {
            System.out.println("异常：" + e.getMessage());
        }
    }
}
