package org.pp.db.jdbc.core;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * ************自强不息************
 *
 * @author 鹏鹏
 * @date 2022/9/16 20:05
 * ************厚德载物************
 **/
public class MapperProxy<T> {

    private final Executor executor;
    private final Class<T> interfaceClass;
    private final InvocationHandler handler;

    public MapperProxy(Executor executor, Class<T> interfaceClass) {
        this.executor = executor;
        this.interfaceClass = interfaceClass;
        this.handler = new DefaultInvocationHandler(executor, interfaceClass);
    }

    public T proxy(InvocationHandler handler) {
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[]{interfaceClass}, handler);
    }

    public T proxy() {
        return proxy(handler);
    }
}
