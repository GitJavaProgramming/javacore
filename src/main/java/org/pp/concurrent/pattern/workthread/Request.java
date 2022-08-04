package org.pp.concurrent.pattern.workthread;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Request {
    private final String name;
    private final int number;
    private static final Random random = new Random();

    public Request(String name, int number) {
        this.name = name;
        this.number = number;
    }

    public void execute() {
        System.out.println(Thread.currentThread().getName() + " executes " + this);
        try {
            TimeUnit.MILLISECONDS.sleep(random.nextInt(1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String toString() {
        return "[name:" + name + ", number:" + number + "]";
    }
}
