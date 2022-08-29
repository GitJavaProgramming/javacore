package org.pp.net.rpc.registry;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标识引用一个远程服务
 * 这个远程服务不需要在本地应用实例化，因为涉及到远程方法调用，通过网络协议实现数据传输
 * 实际上本地使用rpc协议发起网络请求，得到调用返回值
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Reference {
}
