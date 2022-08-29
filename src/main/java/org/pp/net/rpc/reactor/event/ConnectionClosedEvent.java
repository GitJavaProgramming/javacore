package org.pp.net.rpc.reactor.event;

import org.pp.net.rpc.reactor.core.Handler;

public class ConnectionClosedEvent implements Event {
    private Handler handler;

    public ConnectionClosedEvent(Handler handler) {
        this.handler = handler;
    }

    public Handler getHandler() {
        return handler;
    }
}
