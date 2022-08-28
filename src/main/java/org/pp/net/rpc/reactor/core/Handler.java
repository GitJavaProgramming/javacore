package org.pp.net.rpc.reactor.core;


import java.util.concurrent.Future;

public interface Handler extends Runnable {

    void send();

    Future read();

    void connectCloseException();
}
