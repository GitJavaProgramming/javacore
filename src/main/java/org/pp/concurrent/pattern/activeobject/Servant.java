package org.pp.concurrent.pattern.activeobject;

import java.util.concurrent.TimeUnit;

class Servant implements ActiveObject {
    @Override
    public Result makeString(int count, char fillChar) {
        char[] buf = new char[count];
        for (int i = 0; i < count; i++) {
            buf[i] = fillChar;
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return new RealResult(new String(buf));
    }

    @Override
    public void displayString(String text) {
        System.out.println("Display is " + text);
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}