package org.pp.concurrent.pattern.eventdriver;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class AsyncChannel implements Channel<Event>{
    /**
     * 使用线程池的方式处理Message
     */
    private final ExecutorService executorService;

    /**
     * 使用系统自定义的executorService
     */
    public AsyncChannel(){
        this(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2));
    }

    /**
     * 用户自定义的executorService
     * @param executorService
     */
    public AsyncChannel(ExecutorService executorService){
        this.executorService = executorService;
    }

    /**
     * 重写dispatch方法，并且使用final修饰，防止子类重写
     * @param message
     */
    @Override
    public void dispatch(Event message) {
        executorService.submit(() -> this.handle(message));
    }

    /**
     * 提供抽象方法，子类进行具体的实现
     */
    protected abstract void handle(Event message);

    /**
     * 关闭线程池
     */
    void stop(){
        if (null != executorService && !executorService.isShutdown()){
            executorService.shutdown();
        }
    }

}
