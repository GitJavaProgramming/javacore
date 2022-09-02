package org.pp.net.serializable;

import java.io.*;

/**
 * ************自强不息************
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
