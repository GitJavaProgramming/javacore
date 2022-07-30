package org.pp.lang.proxy;

public interface Interceptor {
    default void before() {
        System.out.println("before");
    }

    default void after() {
        System.out.println("after");
    }
}
