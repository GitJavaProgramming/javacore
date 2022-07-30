package org.pp.lang.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class JdkProxyHandler implements InvocationHandler {

    private Interceptor interceptor;
    private Object realObject;

    public JdkProxyHandler() {
    }

    public JdkProxyHandler(Interceptor interceptor) {
        this.interceptor = interceptor;
    }

    public JdkProxyHandler(Object realObject) {
        this.realObject = realObject;
    }

    public JdkProxyHandler(Interceptor interceptor, Object realObject) {
        this.interceptor = interceptor;
        this.realObject = realObject;
    }

    @Override
    public Object invoke(Object proxy/*调用该方法的代理实例*/, Method method, Object[] args) throws Throwable {
        pass();
        interceptor.before();
//        反射操作
        Object result = null;
        if (realObject != null) {
            result = method.invoke(realObject, args);
        }
        interceptor.after();
        return result;
    }

    private void pass() {
        if (interceptor == null) {
            interceptor = new Interceptor() {
            };
            System.out.println("use default interceptor.");
        }
    }
}
