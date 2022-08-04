package org.pp.concurrent.pattern.activeobject;

public final class ActiveObjectFactory {
    public ActiveObjectFactory() {
    }

    public static ActiveObject createAcitveObject() {
        Servant servant = new Servant();
        ActivationQueue queue = new ActivationQueue();
        SchedulerThread schedulerThread = new SchedulerThread(queue);
        ActiveObjectProxy proxy = new ActiveObjectProxy(schedulerThread, servant);
        schedulerThread.start();
        return proxy;
    }
}