package org.pp.lang.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class JdkProxy {

    public static <T> T proxy(Class<T> proxied, InvocationHandler handler) {
        return (T) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), new Class<?>[]{proxied}, handler);
    }
}
