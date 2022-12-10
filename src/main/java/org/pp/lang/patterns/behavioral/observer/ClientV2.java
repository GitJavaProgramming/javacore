package org.pp.lang.patterns.behavioral.observer;

import org.pp.lang.patterns.behavioral.observer.v2.Observable;

/**
 * ************自强不息************
 *
 * @author 鹏鹏
 * @date 2022/12/9 21:03
 * ************厚德载物************
 **/
public class ClientV2 {

    public static void main(String[] args) {
        Observable.just("hello world"/*被观察者状态*/).subscribeOne(System.out::println/*观察者订阅，执行动作*/);
        Observable.just("test")
                .add(s -> System.out.println(s))
                .add(s -> System.out.println(s))
                .subscribeAll();
    }
}
