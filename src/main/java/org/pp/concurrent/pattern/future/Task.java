package org.pp.concurrent.pattern.future;

@FunctionalInterface
public interface Task<IN, OUT> {
    OUT get(IN input);
}
