package org.pp.net.rpc.reactor.core;

import org.pp.net.rpc.registrationcenter.connect.AcceptorHandler;

public class PipeLineImpl implements PipeLine {

    private final AcceptorHandler acceptorHandler;

    public PipeLineImpl(AcceptorHandler acceptorHandler) {
        this.acceptorHandler = acceptorHandler;
    }

    public void addLast() {

    }
}
