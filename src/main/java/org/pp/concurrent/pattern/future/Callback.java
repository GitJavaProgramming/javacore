package org.pp.concurrent.pattern.future;

@FunctionalInterface
public interface Callback<T> {
    void call(T t);
}
