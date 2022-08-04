package org.pp.concurrent.pattern.future;

import java.util.concurrent.atomic.AtomicInteger;

public class FutureServiceImpl<IN,OUT> implements FutureService<IN,OUT> {
    private final static String FUTURE_THREAD_PREFIX = "FUTURE-";
    private final AtomicInteger nextCounter = new AtomicInteger(0);

    private String getNextName() {
        return FUTURE_THREAD_PREFIX + nextCounter.getAndIncrement();
    }

    @Override
    public Future<?> submit(Runnable runnable) {
        final FutureTask<Void> futuretask = new FutureTask<>();
        new Thread(()-> {
            runnable.run();
            futuretask.finish(null);
        }, getNextName()).start();
        return futuretask;
    }

    @Override
    public Future<OUT> submit(Task<IN, OUT> task, IN input) {
        final FutureTask<OUT> futuretask = new FutureTask<>();
        new Thread(()-> {
            OUT result = task.get(input);
            futuretask.finish(result);
        }, getNextName()).start();
        return futuretask;
    }

    @Override
    public Future<OUT> submit(Task<IN, OUT> task, IN input, Callback<OUT> callback) {
        final FutureTask<OUT> futuretask = new FutureTask<>();
        new Thread(()-> {
            OUT result = task.get(input);
            futuretask.finish(result);
            if(callback != null) {
                callback.call(result);
            }
        }, getNextName()).start();
        return futuretask;
    }
}
