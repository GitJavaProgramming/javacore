package org.pp.concurrent.pattern.future;

public interface Future<T> {
    T get() throws InterruptedException;

    boolean done();
}
