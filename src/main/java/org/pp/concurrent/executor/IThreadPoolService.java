package org.pp.concurrent.executor;

public interface IThreadPoolService {
    void startup();

    void submit(Runnable task);

    void shutdown();
}
