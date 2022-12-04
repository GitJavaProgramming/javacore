package org.pp.lang.patterns.behavioral.command;

/**
 * ************自强不息************
 *
 * @author 鹏鹏
 * @date 2022/12/4 8:04
 * ************厚德载物************
 **/
public interface Command {
    void execute();

    default String name() {
        return this.getClass().getSimpleName();
    }
}
