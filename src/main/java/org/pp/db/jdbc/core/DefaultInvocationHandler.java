package org.pp.db.jdbc.core;

import java.lang.reflect.*;
import java.util.*;
import java.util.concurrent.*;

/**
 * ************自强不息************
 *
 * @author 鹏鹏
 * @date 2022/9/16 20:09
 * ************厚德载物************
 **/
public class DefaultInvocationHandler<T> implements InvocationHandler {

    private final Map<Class, Set<MethodWrapper>> methodCache = new ConcurrentHashMap<>();
    private final Executor executor;
    private final Class<T> interfaceClass;
    private final List<Interceptor> interceptors = new ArrayList<>();

    public DefaultInvocationHandler(Executor executor, Class<T> interfaceClass) {
        this.executor = executor;
        this.interfaceClass = interfaceClass;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return exec(method, args);
    }

    private Object exec(Method method, Object[] args) {
        Future future = null;
        Set<MethodWrapper> wrapperSet = methodCache.getOrDefault(interfaceClass, new HashSet<>());
//        if(!wrapperSet.isEmpty()) { }
        MethodWrapper methodWrapper = new MethodWrapper();
        methodWrapper.setMethodName(method.getName());
        Class<?> returnType = null;
        Type genericReturnType = method.getGenericReturnType();
        if (genericReturnType instanceof ParameterizedType) {
            returnType = (Class) ((ParameterizedType) genericReturnType).getActualTypeArguments()[0]; // 一层嵌套的泛型
        } else {
            returnType = Object.class;
        }
        methodWrapper.setReturnType(returnType);
        String sql = null;
        if (method.isAnnotationPresent(Select.class)) {
            Select select = method.getAnnotation(Select.class);
            sql = select.sql();
            methodWrapper.setSql(sql);
        }
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            String paramName = parameter.getName();
            if (parameter.isAnnotationPresent(Param.class)) {
                Param param = parameter.getAnnotation(Param.class);
                paramName = param.value();
            }
            Object paramValue = args[i];
            methodWrapper.getObjList().add(new MethodWrapper.Obj(paramName, paramValue));
        }
        future = executor.execute(methodWrapper);
        wrapperSet.add(methodWrapper);
        try {
            return future.get(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } finally {
//            executor.shutdown();
        }
        return null;
    }
}
