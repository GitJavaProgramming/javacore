package org.pp.lang.reflection;

import org.junit.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class ReflectionTest {

    /**
     * 见org.springframework.core.ResolvableType.resolveGeneric(...)
     * DefaultListableBeanFactory中
     * resolveDependency->resolveMultipleBeans->findAutowireCandidates
     */
    @Test
    public void test2() {
        class WebMvcConfigurer {
        }
        class DelegatingWebMvcConfiguration {
            @Inject
            public void setConfigurers(List<WebMvcConfigurer> configurers) {
            }
        }
        // springmvc为DelegatingWebMvcConfiguration注入List<WebMvcConfigurer> configurers
        Class clazz = DelegatingWebMvcConfiguration.class;
        Method[] declaredMethods = clazz.getDeclaredMethods();
        for (Method method : declaredMethods) {
            boolean annotationPresent = method.isAnnotationPresent(Inject.class);
            if (annotationPresent) {
                Parameter parameter = method.getParameters()[0];
                Type[] actualTypeArguments = ((ParameterizedType) parameter.getDeclaringExecutable().getGenericParameterTypes()[0]).getActualTypeArguments();
                System.out.println(actualTypeArguments[0].getTypeName());
                System.out.println(parameter.getParameterizedType());
                System.out.println(parameter.getAnnotatedType());
            }
        }
    }


    @Test
    public void test1() throws ClassNotFoundException {
        Class clazz = Class.forName("org.pp.lang.cglib.TargetObject");
        Method[] declaredMethods = clazz.getDeclaredMethods();
        for (Method method : declaredMethods) {
            for (Parameter parameter : method.getParameters()) {
                System.out.println(method.getName() + "==" + parameter.getName() + parameter.getType());
            }
        }
    }
}
