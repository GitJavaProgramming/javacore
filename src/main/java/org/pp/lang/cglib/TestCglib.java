package org.pp.lang.cglib;

import net.sf.cglib.proxy.Enhancer;

/**
 * 参考
 * https://www.runoob.com/w3cnote/cglibcode-generation-library-intro.html
 * java8之后不允许深度反射加上vm参数
 * --add-opens java.base/java.lang=ALL-UNNAMED
 */
public class TestCglib {
    public static void main(String args[]) {
        Enhancer enhancer =new Enhancer();
        enhancer.setSuperclass(TargetObject.class);
        enhancer.setCallback(new TargetInterceptor());
        TargetObject targetObject2=(TargetObject)enhancer.create();
        System.out.println(targetObject2);
        System.out.println(targetObject2.method1("mmm1"));
        System.out.println(targetObject2.method2(100));
        System.out.println(targetObject2.method3(200));
    }
}
