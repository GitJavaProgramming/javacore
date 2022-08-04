package org.pp.concurrent.pattern.eventbus;

public interface EventExceptionHandler {
    void handler(Throwable cause, EventContext context);
}
