package org.pp.net.rpc.reactor.core;


public interface Handler extends Runnable {

    default public void connectCloseException() {
        System.out.println("连接关闭时异常...");
    }

    default public void conectionException() {
        System.out.println("连接异常...");
    }
}
