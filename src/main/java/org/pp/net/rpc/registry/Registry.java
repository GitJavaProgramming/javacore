package org.pp.net.rpc.registry;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Registry {

    /**
     * <接口,实现类>
     */
    private final Map<Class, Object> instanceCache = new ConcurrentHashMap<>();
    /**
     * <接口,方法签名列表>
     */
    private final Map<Class, Set<MethodWrapper>> methodCache = new ConcurrentHashMap<>();

    public void scanAll() {
        // 扫描项目中所有service

        // 这里只扫描UserService
        scanAndRegisterService("org.pp.net.rpc.registry.Service.UserService");
    }

    /**
     * 扫描所有需要暴露的服务
     */
    public final void scanAndRegisterService(String name) {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        try {
            Class<?> aClass = classLoader.loadClass(name);
            if (aClass.isAnnotationPresent(Service.class)) {
                // 缓存接口实现类
                ServiceLoader serviceLoader = ServiceLoader.load(aClass);
                Object obj = serviceLoader.findFirst().get();
                instanceCache.put(aClass, obj);
                // 缓存接口--方法列表
                Set<MethodWrapper> set = new TreeSet<>();
                for (Method method : aClass.getDeclaredMethods()) {
                    MethodWrapper wrapper = new MethodWrapper();
                    wrapper.setInterfaceType(aClass);
                    wrapper.setReturnType(method.getReturnType());
                    wrapper.setMethodName(method.getName());
                    wrapper.setArgsTypeArray(method.getParameterTypes());
                    set.add(wrapper);
                }
                methodCache.putIfAbsent(aClass, set);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public final Map<Class, Object> getInstanceCache() {
        return Collections.unmodifiableMap(instanceCache);
    }

    public final Map<Class, Set<MethodWrapper>> getMethodCache() {
        return Collections.unmodifiableMap(methodCache);
    }
}
