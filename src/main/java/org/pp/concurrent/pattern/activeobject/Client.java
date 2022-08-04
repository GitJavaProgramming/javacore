package org.pp.concurrent.pattern.activeobject;

public class Client {
    public static void main(String[] args) {
        ActiveObject activeObject = ActiveObjectFactory.createAcitveObject();
        new MakeClientThread(activeObject, "Alice").start();
        new MakeClientThread(activeObject, "Bobby").start();

        new DisplayClientThread("Chris", activeObject).start();
    }
}
