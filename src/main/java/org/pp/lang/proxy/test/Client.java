package org.pp.lang.proxy.test;

import org.pp.lang.proxy.JdkProxy;
import org.pp.lang.proxy.JdkProxyHandler;

import java.lang.reflect.InvocationTargetException;

public class Client {
    public static void main(String[] args) {
        Service service = JdkProxy.proxy(Service.class/*代理的接口*/, new JdkProxyHandler(createService(Service1.class)/*真实的代理对象*/));
//        service = JdkProxy.proxy(Service.class/*代理的接口*/, new JdkProxyHandler());
        service.say();
    }

    private static <T> T createService(Class<T> clazz) {
        try {
            return clazz.getConstructor().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }
}
