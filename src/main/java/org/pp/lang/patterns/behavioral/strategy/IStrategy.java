package org.pp.lang.patterns.behavioral.strategy;

public interface IStrategy {
    void fetch();

    default IStrategy getInstance() throws IllegalAccessException, InstantiationException {
        return getClass().newInstance();
    }
}
