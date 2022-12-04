package org.pp.lang.patterns.creator.prototype;

import java.util.ArrayList;
import java.util.List;

/**
 * ************自强不息************
 *
 * @author 鹏鹏
 * @date 2022/12/4 18:51
 * ************厚德载物************
 **/
public class Client2 {
    public static void main(String[] args) {
        // 创建原型对象
        ConcretePrototype prototype = new ConcretePrototype();
        prototype.setAge(18);
        prototype.setName("耀");
        List<String> hobbies = new ArrayList<>();
        hobbies.add("对线孙策");
        hobbies.add("对象铠");
        prototype.setHobbies(hobbies);

        // 拷贝原型对象
        ConcretePrototype cloneType = prototype.deepCloneHobbies();
        cloneType.getHobbies().add("对线吕布");

        System.out.println("原型对象：" + prototype);
        System.out.println("克隆对象：" + cloneType);
        System.out.println(prototype == cloneType);

        System.out.println("原型对象的爱好：" + prototype.getHobbies());
        System.out.println("克隆对象的爱好：" + prototype.getHobbies());
        System.out.println(prototype.getHobbies() == cloneType.getHobbies());
    }
}

