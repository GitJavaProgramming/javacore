package org.pp.lang.patterns.creator.singleton;

/**
 * ************自强不息************
 *
 * @author 鹏鹏
 * @date 2022/12/4 19:00
 * ************厚德载物************
 **/
public class Singleton {
    private static final Singleton instance = new Singleton();

    private Singleton() {
    }

    public static Singleton getInstance() {
        return instance;
    }

    public static Singleton getInstance2() {
        return Holder.instance;
    }

    private enum SingletonEnum {
        INSTANCE;
    }

    private static final class Holder {
        private static final Singleton instance = new Singleton();
    }
}
