package org.pp.net.rpc.reactor.core;

import java.nio.ByteBuffer;

public abstract class AbstractIOHandler implements Handler {
    protected static final int READING = 0, SENDING = 1;
    protected final ByteBuffer input = ByteBuffer.allocate(1024);
    protected final ByteBuffer output = ByteBuffer.allocate(1024);
    protected int state = READING;

    public AbstractIOHandler() {
    }

    @Override
    public void run() {
        if (state == READING) {
            read();
        } else if (state == SENDING) {
            send();
        }
    }

    @Override
    public void connectCloseException() {
        System.out.println("连接关闭时异常...");
    }

    public void conectionException() {
        System.out.println("连接异常...");
    }
}
