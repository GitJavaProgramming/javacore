package org.pp.lang.patterns.behavioral.observer.v2;

import java.util.List;
import java.util.function.Consumer;

/**
 * ************自强不息************
 *
 * @author 鹏鹏
 * @date 2022/12/9 20:32
 * ************厚德载物************
 **/
public abstract class Observable<T> {

    public static <T> Observable just(T item) {
        return new ObservableJust(item);
    }

    public final void subscribeOne(Consumer<T> consumer) {
        subscribeActual(new LambdaObserver<>(consumer));
    }

    protected abstract void subscribeActual(LambdaObserver<? super T> observer);

    public abstract Observable<T> add(Consumer<T> consumer);

    /**
     * 只能给子类覆写，不能提供外部访问
     */
    protected abstract List<LambdaObserver> getObservers();

    public final void subscribeAll() {
        getObservers().forEach(o -> subscribeActual(o));
    }
}
