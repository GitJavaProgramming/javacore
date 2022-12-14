package org.pp.lang.patterns.behavioral.observer.v1;

public class Observer {

    private final String name;

    public Observer(String name) {
        this.name = name;
    }

    public synchronized void update(Observable observable) {
        System.out.println(name + "->" + observable.getState());
        if (observable.getState() == Observable.State.FOLLOWING) {
            observable.setState(Observable.State.LOOKING);
        }
        System.out.println(name + "->" + observable.getState());
        observable.stop();
    }
}
