package org.pp.net.rpc.registry;

import org.pp.net.rpc.utils.Hessian2Utils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * ************自强不息************
 *
 * @author 鹏鹏
 * @date 2022/9/2 11:42
 * ************厚德载物************
 **/
public class ProxyFactory {
    private static final Registry registry = Registry.instance;

    public static <T> T getProxy(Class interfaceClass) {
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[]{interfaceClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                MethodWrapper wrapper = new MethodWrapper();
                wrapper.setInterfaceType(interfaceClass);
                wrapper.setMethodName(method.getName());
                wrapper.setArgs(args);
                Socket socket = new Socket("localhost", 9999);
                socket.getOutputStream().write(Hessian2Utils.serialize(wrapper));
                socket.getOutputStream().flush();

                byte[] bytes = new byte[1024];
                socket.getInputStream().read(bytes);
                Object result = Hessian2Utils.deserialize(bytes.toString().trim().getBytes(StandardCharsets.UTF_8));
                return result;
            }
        });
    }
}
