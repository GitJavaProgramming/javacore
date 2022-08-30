package org.pp.net.rpc.registrationcenter.connect;

import org.pp.net.rpc.registry.MethodWrapper;
import org.pp.net.rpc.registry.Registry;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Set;

public class Connect {

    private final Registry registry = new Registry();

    public Connect() {
        registry.scanAll();
    }

    public void connectRegistrationCenter(String host, int port) {
        try {
            Socket socket = new Socket(host, port);
            Map<Class, Set<MethodWrapper>> methodCache = registry.getMethodCache();
            // 向注册中心发送方法缓存
            methodCache.forEach((clazz, wrapperSet) -> wrapperSet.forEach(m -> {
                try {
                    // 这里要指定发送协议好进行封包拆包
                    ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                    outputStream.write(m.toString().getBytes(StandardCharsets.UTF_8).length); // header
                    outputStream.writeObject(m); // body
                    outputStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
