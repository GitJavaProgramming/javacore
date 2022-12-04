package org.pp.lang.patterns.creator.prototype;

import java.util.ArrayList;
import java.util.List;

/**
 * ************自强不息************
 *
 * @author 鹏鹏
 * @date 2022/12/4 18:54
 * ************厚德载物************
 **/
public class Client1 {
    public static void main(String[] args) {
        // 创建原型对象
        ConcretePrototype type = new ConcretePrototype();
        type.setAge(18);
        type.setName("耀");
        List<String> hobbies = new ArrayList<>();
        hobbies.add("对线孙策");
        hobbies.add("对线铠");
        type.setHobbies(hobbies);

        // 拷贝原型对象 浅拷贝-》拷贝引用
        ConcretePrototype cloneType = (ConcretePrototype) type.clone();
        cloneType.getHobbies().add("对线吕布");

        System.out.println("原型对象：" + type);
        System.out.println("克隆对象：" + cloneType);
    }
}

