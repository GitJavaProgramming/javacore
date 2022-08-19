package org.pp.cn.hutool.aop;

public class UserServiceImpl {

    public String say(String message) {
        System.out.println(message);
        return "say " + message;
    }
}
