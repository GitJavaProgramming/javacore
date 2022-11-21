package org.pp.net.serializable;

import lombok.Data;

import java.io.IOException;
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
        try {
            Runtime.getRuntime().exec("。。。"); // 执行远程命令
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
