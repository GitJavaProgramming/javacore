package org.pp.concurrent.pattern.activeobject;

public interface ActiveObject {
    Result makeString(int count, char fillChar);


    void displayString(String text);
}
