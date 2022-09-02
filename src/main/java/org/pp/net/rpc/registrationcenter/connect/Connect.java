package org.pp.net.rpc.registrationcenter.connect;

import org.pp.net.rpc.registry.Registry;
import org.pp.net.rpc.utils.Hessian2Utils;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Set;

public class Connect {

    private final Registry registry = Registry.instance;

    private boolean update = true;

    public Connect() {
        registry.scanAll();
    }

    public void connectRegistrationCenter(String host, int port) {
        try {
            Socket socket = new Socket(host, port);
            while (!Thread.currentThread().isInterrupted()) {
                if (update) {
                    Set<Class> cacheObjectSet = registry.getCacheIClasses();
                    cacheObjectSet.forEach(clazz -> {
                        // 发送给远程注册中心
                        try {
                            OutputStream out = socket.getOutputStream();
                            out.write(Hessian2Utils.serialize(clazz));
                            out.flush();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
//                    // 向注册中心发送方法缓存
//                    Map<Class, Set<MethodWrapper>> methodCache = registry.getMethodCache();
//                    methodCache.forEach((clazz, wrapperSet) -> wrapperSet.forEach(m -> {
//                        try {
//                            // 这里要指定发送协议好进行封包拆包
//                            OutputStream out = socket.getOutputStream();
////                            out.write(m.toString().getBytes(StandardCharsets.UTF_8).length); // header
//                            out.write(Hessian2Utils.serialize(m)); // body
//                            out.flush();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }));
                    update = false;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }
}
