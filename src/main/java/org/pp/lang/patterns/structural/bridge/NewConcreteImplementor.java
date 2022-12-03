package org.pp.lang.patterns.structural.bridge;

public class NewConcreteImplementor extends DefaultImplementor {

    @Override
    public void doActionImpl() {
        System.out.println("NewConcreteImplementor");
    }
}
