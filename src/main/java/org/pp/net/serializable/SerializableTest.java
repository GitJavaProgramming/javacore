package org.pp.net.serializable;

import java.io.*;

/**
 * ************自强不息************
 * 反序列化漏洞 https://su18.org/post/ysoserial-su18-1/#%E4%BA%8C-%E5%BA%8F%E5%88%97%E5%8C%96%E4%B8%8E%E5%8F%8D%E5%BA%8F%E5%88%97%E5%8C%96
 *
 * @author 鹏鹏
 * @date 2022/9/2 7:22
 * ************厚德载物************
 **/
public class SerializableTest {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Person p = new Person();
        p.setAge(10);

        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("abc.txt"));
        out.writeObject(p);
        out.close();

        FileInputStream fileInputStream = new FileInputStream("abc.txt");
        System.out.println(fileInputStream.available());
        ObjectInputStream in = new ObjectInputStream(fileInputStream);
        in.readObject();
        in.close();
    }
}
