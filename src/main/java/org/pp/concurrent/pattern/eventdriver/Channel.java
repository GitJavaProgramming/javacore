package org.pp.concurrent.pattern.eventdriver;

public interface Channel<E extends Message> {

    void dispatch(E message);
}
