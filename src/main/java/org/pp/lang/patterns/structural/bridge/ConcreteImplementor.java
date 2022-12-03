package org.pp.lang.patterns.structural.bridge;

public class ConcreteImplementor extends DefaultImplementor {

    @Override
    public void doActionImpl() {
        System.out.println("ConcreteImplementor");
    }
}
