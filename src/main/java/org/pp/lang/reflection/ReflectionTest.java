package org.pp.lang.reflection;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class ReflectionTest {
    public static void main(String[] args) throws ClassNotFoundException {
        Class clazz = Class.forName("org.pp.lang.cglib.TargetObject");
        Method[] declaredMethods = clazz.getDeclaredMethods();
        for (Method method : declaredMethods) {
            for (Parameter parameter : method.getParameters()) {
                System.out.println(method.getName() + "==" + parameter.getName() + parameter.getType());
            }
        }
    }
}
