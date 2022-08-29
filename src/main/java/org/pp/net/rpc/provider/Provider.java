package org.pp.net.rpc.provider;

import org.pp.net.rpc.registrationcenter.connect.Connect;
import org.pp.net.rpc.registry.RpcServer;

public class Provider {
    public static void main(String[] args) {
        // 本地扫描应用内需要暴露的服务（方法）并缓存
//        Registry registry = new Registry();
//        registry.scanAndRegisterService("org.pp.net.rpc.registry.Service.UserService");
        // 连接注册中心 发送服务缓存到注册中心-缓存
        Connect connect = new Connect();
        connect.connectRegistrationCenter("localhost", 9090); // 连接注册中心
        // 启动rpc服务端，使用协议进行方法调用  服务端维护<接口,实现类>缓存
        RpcServer rpcServer = new RpcServer();
        rpcServer.start(9999);
    }
}
