package org.pp.net.rpc;

import org.pp.net.rpc.reactor.core.Bootstrap;

import java.io.IOException;

public class Server {
    public static void main(String[] args) {
        Bootstrap bootstrap = new Bootstrap();
        try {
            bootstrap.bind(9090).init();
        } catch (IOException e) {
            System.out.println("异常：" + e.getMessage());
        }
    }
}
