package org.pp.cn.hutool.aop;

import cn.hutool.aop.ProxyUtil;
import cn.hutool.aop.aspects.TimeIntervalAspect;

/**
 * java8之后不允许深度反射加上vm参数
 * --add-opens java.base/java.lang=ALL-UNNAMED
 */
public class AopTest {
    public static void main(String[] args) {
        // java.lang.IllegalArgumentException
        UserServiceImpl userService = new UserServiceImpl();
        TimeIntervalAspect timeIntervalAspect = new TimeIntervalAspect();
        UserServiceImpl service = ProxyUtil.proxy(userService, timeIntervalAspect);
        service.say("hello");
    }
}
