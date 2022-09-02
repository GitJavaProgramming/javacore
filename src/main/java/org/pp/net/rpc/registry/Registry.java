package org.pp.net.rpc.registry;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Registry {

    public static final Registry instance = new Registry();

    /**
     * <接口,实现类>
     */
    private final Map<Class, Object> localInstanceCache = new ConcurrentHashMap<>();
    private final Set<Class> cacheIClasses = new HashSet<>();

    private Registry() {
    }

    public void scanAll() {
        // 扫描项目中所有service

        // 这里只扫描UserService
        scanAndRegisterService("org.pp.net.rpc.provider.UserService");
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
                ServiceLoader<?> serviceLoader = ServiceLoader.load(aClass);
                Iterator<?> iterator = serviceLoader.iterator();
                Object obj = null;
                while (iterator.hasNext()) {
                    obj = iterator.next();
                    Field[] fields = obj.getClass().getDeclaredFields();
                    for (Field field : fields) {
                        if (field.isAnnotationPresent(Reference.class)) {
                            Object f = ProxyFactory.getProxy(field.getClass());
                            field.set(obj, f);
                        }
                    }
                }
                localInstanceCache.put(aClass, obj);
                cacheIClasses.add(aClass);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    public Set<Class> getCacheIClasses() {
        return Collections.unmodifiableSet(cacheIClasses);
    }

    public final Map<Class, Object> getLocalInstanceCache() {
        return Collections.unmodifiableMap(localInstanceCache);
    }

}
