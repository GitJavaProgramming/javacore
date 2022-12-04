package org.pp.concurrent.pattern.eventdriver;

public class Client {
    public static void main(String[] args) {
        SyncEventDispatcher dispatcher = new SyncEventDispatcher();
        dispatcher.registerChannel(EventDispatcherExample.InputEvent.class, new EventDispatcherExample.InputEventHandler(dispatcher));
        dispatcher.registerChannel(EventDispatcherExample.ResultEvent.class, new EventDispatcherExample.ResultEventHandler());
        dispatcher.dispatch(new EventDispatcherExample.InputEvent(1, 2));
    }
}
