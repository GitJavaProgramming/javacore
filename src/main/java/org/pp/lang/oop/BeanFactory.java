package org.pp.lang.oop;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class BeanFactory {
    public static void main(String[] args) {
        String dir = System.getProperty("user.dir");
        dir = System.getProperty("java.class.path");
        Package p = BeanFactory.class.getPackage();
        dir = p.getName();
        System.out.println(dir);
        boolean flag = p.isAnnotationPresent(OOP.class);
        Class<BeanImpl1> clazz = BeanImpl1.class;
        Autowired autowired = clazz.getAnnotation(Autowired.class);
        Class<?> bean = autowired.name();
        BeanImpl1 beanImpl1;
        try {
            Constructor<?> constructor = clazz.getConstructor();
            constructor.setAccessible(true);
            beanImpl1 = (BeanImpl1) constructor.newInstance();
            Object obj = bean.getConstructor().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
