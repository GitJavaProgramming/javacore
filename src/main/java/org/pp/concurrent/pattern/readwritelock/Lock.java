package org.pp.concurrent.pattern.readwritelock;

public interface Lock {
    void lock() throws InterruptedException;

    void unlock();
}
