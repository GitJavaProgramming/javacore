package org.pp.lang.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class JdkCommonProxyHandler implements InvocationHandler {

    private final Interceptor interceptor;
    private final Object realObject;

    public JdkCommonProxyHandler(Interceptor interceptor, Object realObject) {
        this.interceptor = interceptor;
        this.realObject = realObject;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        interceptor.before();
        method.invoke(realObject, args);
        interceptor.after();
        return null;
    }
}
