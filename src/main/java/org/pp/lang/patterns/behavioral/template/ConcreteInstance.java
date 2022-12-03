package org.pp.lang.patterns.behavioral.template;

public class ConcreteInstance extends AbstractTemplate {

    @Override
    public void templateMethod() {
        System.out.println("instance");
    }
}
