package org.pp.net.socket;

public class ServerOptions {
    private volatile boolean stop = false;

    public boolean canStop() {
        return stop;
    }
}
