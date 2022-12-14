package org.pp.lang.patterns.structural.bridge;

public class NewConcreteAction extends Abstraction {

    public NewConcreteAction(Implementor implementor/*构造注入*/) {
        this.implementor = implementor;
    }

    @Override
    public void doAction() {
        implementor.doActionImpl();
    }
}
