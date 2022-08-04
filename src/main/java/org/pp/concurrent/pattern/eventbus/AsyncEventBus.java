package org.pp.concurrent.pattern.eventbus;

import java.util.concurrent.ThreadPoolExecutor;

public class AsyncEventBus extends EventBus {
    AsyncEventBus(String busName, EventExceptionHandler exceptionHandler, ThreadPoolExecutor executor) {
        super(busName, exceptionHandler, executor);
    }

    AsyncEventBus(String busName, ThreadPoolExecutor executor) {
        this(busName, null, executor);
    }

    AsyncEventBus(ThreadPoolExecutor executor) {
        this("default_async", null, executor);
    }

    AsyncEventBus(EventExceptionHandler exceptionHandler, ThreadPoolExecutor executor) {
        this("default_async", exceptionHandler, executor);
    }
}
