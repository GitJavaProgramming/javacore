package org.pp.net.serializable;

import lombok.Data;

import java.io.ObjectInputStream;
import java.io.Serializable;

/**
 * ************自强不息************
 *
 * @author 鹏鹏
 * @date 2022/9/2 7:17
 * ************厚德载物************
 **/
@Data
public class Person implements Serializable {
    private int age;

    private void readObject(ObjectInputStream objectInputStream) {
        System.out.println("你好你好");
    }
}
