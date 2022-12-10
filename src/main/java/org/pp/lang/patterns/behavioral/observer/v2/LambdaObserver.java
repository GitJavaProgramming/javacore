package org.pp.lang.patterns.behavioral.observer.v2;

import java.util.function.Consumer;

/**
 * ************自强不息************
 *
 * @author 鹏鹏
 * @date 2022/12/9 20:55
 * ************厚德载物************
 **/
public class LambdaObserver<T> {
    private Consumer<T> consumer;

    public LambdaObserver(Consumer<T> consumer) {
        this.consumer = consumer;
    }

    public void update(T t) {
        consumer.accept(t);
    }
}
