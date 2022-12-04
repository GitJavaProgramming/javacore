package org.pp.lang.patterns.behavioral.memento;

/**
 * ************自强不息************
 * 备忘录模式
 * 储存对象的历史状态，以备恢复对象的状态
 *
 * @author 鹏鹏
 * @date 2022/12/4 9:40
 * ************厚德载物************
 **/
public class Client {
    public static void main(String[] args) {
        Originator originator = new Originator();
        originator.setState("状态1");
        originator.setState("状态2");
        originator.setState("状态3");
        originator.history().forEach((key, value) -> System.out.println(key + value));
        originator.showHistory();
    }
}
