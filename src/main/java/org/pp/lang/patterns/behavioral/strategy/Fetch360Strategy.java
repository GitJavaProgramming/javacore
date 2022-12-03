package org.pp.lang.patterns.behavioral.strategy;

public class Fetch360Strategy implements IStrategy {

    @Override
    public void fetch() {
        System.out.println("360");
    }
}
