package org.pp.lang.patterns.creator.factory;

/**
 * ************自强不息************
 *
 * @author 鹏鹏
 * @date 2022/12/4 19:15
 * ************厚德载物************
 **/
public class FactoryA implements IFactory{
    @Override
    public IA createA() {
        return new AA();
    }

    @Override
    public IB createB() {
        return new BB();
    }
}
