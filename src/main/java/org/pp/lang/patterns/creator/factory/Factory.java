package org.pp.lang.patterns.creator.factory;

/**
 * ************自强不息************
 *
 * @author 鹏鹏
 * @date 2022/12/4 19:11
 * ************厚德载物************
 **/
public class Factory implements IFactory {
    @Override
    public IA createA() {
        return new A();
    }

    @Override
    public IB createB() {
        return new B();
    }
}
