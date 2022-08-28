package org.pp.net.rpc.reactor.listener;

import org.pp.net.rpc.reactor.event.Event;

public interface Listener {
    void onConnectionClosed(Event event);

    void onConnectionException(Event event, Exception e);
}
