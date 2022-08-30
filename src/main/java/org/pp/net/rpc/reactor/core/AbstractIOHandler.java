package org.pp.net.rpc.reactor.core;

import java.nio.ByteBuffer;
import java.util.concurrent.Future;

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

    public abstract void send();

    public abstract Future read();
}
