package org.pp.db.jdbc.core;

public interface Interceptor {
    default void before() {
        System.out.println("before");
    }

    default void after() {
        System.out.println("after");
    }
}
