package org.pp.concurrent.pattern.eventbus;

public interface Bus {
    // 将某个对象注册到 Bus 上，从此之后该类就成为了 Subscriber 了
    void register(Object subscriber);

    // 将某个对象从 Bus 上取消注册，取消注册之后就不会再接受到来自 Bus 的任何消息
    void unregister(Object subscriber);

    // 提交 Event 到默认的 topic
    void post(Object event);

    // 提交 Event 到指定的 topic
    void post(Object Event, String topic);

    // 关闭该 bus
    void close();

    // 返回 Bus 的名称标识
    String getBusName();
}
