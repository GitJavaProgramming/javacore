package org.pp.lang.patterns.behavioral.observer.v2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

/**
 * ************自强不息************
 *
 * @author 鹏鹏
 * @date 2022/12/9 20:45
 * ************厚德载物************
 **/
public final class ObservableJust<T> extends Observable<T> {
    private final T value;

    private final List<LambdaObserver> observers = new ArrayList<>();

    public ObservableJust(final T value) {
        this.value = value;
    }

    @Override
    protected void subscribeActual(LambdaObserver<? super T> observer) {
        observer.update(value);
    }

    @Override
    public Observable<T> add(Consumer<T> consumer) {
        observers.add(new LambdaObserver(consumer));
        return this;
    }

    @Override
    protected List<LambdaObserver> getObservers() {
        return Collections.unmodifiableList(observers);
    }
}
