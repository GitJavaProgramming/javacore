package org.pp.concurrent.pattern.eventbus;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Registry {
    // 存储 Subscriber 集合和 topic 之间关系的 map
    private final ConcurrentHashMap<String, ConcurrentLinkedQueue<Subscriber>> subscriberContainer = new ConcurrentHashMap<>();

    public void bind(Object subscriber) {
        // 获取 Subscriber Object 的方法集合，然后进行绑定
        List<Method> subscribeMethods = getSubscribeMethods(subscriber);
        subscribeMethods.forEach(m -> tierSubscriber(subscriber, m));
    }

    public void unbind(Object subscriber) {
        // unbind 为了提高速度，只对 Subscriber 进行失效操作
        subscriberContainer.forEach((key, queue) ->
                queue.forEach(s -> {
                    if (s.getSubscribeObject() == subscriber) {
                        s.setDisable(true);
                    }
                })
        );
    }

    private void tierSubscriber(Object subscriber, Method method) {
        final Subscribe subscribe = method.getDeclaredAnnotation(Subscribe.class);
        String topic = subscribe.topic();
        // 当某个 topic 没有 Subscriber Queue 的时候创建一个
        subscriberContainer.computeIfAbsent(topic, key -> new ConcurrentLinkedQueue<>());
        // 创建一个 subscriber 并且加入 subscriber 列表中
        subscriberContainer.get(topic).add(new Subscriber(subscriber, method));
    }

    public ConcurrentLinkedQueue<Subscriber> scanSubscriber(final String topic) {
        return subscriberContainer.get(topic);

    }

    private List<Method> getSubscribeMethods(Object subcriber) {
        final List<Method> methods = new ArrayList<>();
        Class<?> temp = subcriber.getClass();
        // 不断获取所有的方法
        while (temp != null) {
            // 获取所有的方法
            Method[] declaredMethods = temp.getDeclaredMethods();
            // 只有 public 方法 && 有一个入参 && 被 @Subscribe 标记的方法才符合回调方法
            Arrays.stream(declaredMethods)
                    .filter(m -> m.isAnnotationPresent(Subscribe.class)
                            && m.getParameterCount() == 1
                            && m.getModifiers() == Modifier.PUBLIC)
                    .forEach(methods::add);
            temp = temp.getSuperclass();
        }
        return methods;
    }

}
