package org.pp.concurrent.pattern.eventdriver;

import java.util.concurrent.TimeUnit;

public class AsyncEventDispatcherExample {

    //主要用于处理 InputEvent，但是需要继承 AsyncChannel

    public static void main(String[] args) {

        //定义 AsyncEventDispatcher

        AsyncEventDispatcher dispatcher = new AsyncEventDispatcher();


        //注册 Event 和 Channel 之间的关系

        dispatcher.registerChannel(EventDispatcherExample.InputEvent.class, new AsyncInputEventHandler(dispatcher));
        dispatcher.registerChannel(EventDispatcherExample.ResultEvent.class, new AsyncResultEventHandler());
        dispatcher.dispatch(new EventDispatcherExample.InputEvent(1, 2));
    }


    //主要用于处理 InputEvent，但是需要继承 AsyncChannel

    static class AsyncInputEventHandler extends AsyncChannel {

        private final AsyncEventDispatcher dispatcher;

        AsyncInputEventHandler(AsyncEventDispatcher dispatcher) {
            this.dispatcher = dispatcher;

        }

        //不同于以同步的方式实现 dispatch，异步的方式需要实现 handle

        @Override

        protected void handle(Event message) {
            EventDispatcherExample.InputEvent inputEvent = (EventDispatcherExample.InputEvent) message;
            System.out.printf("X:%d,Y:%d\n", inputEvent.getX(), inputEvent.getY());
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int result = inputEvent.getX() + inputEvent.getY();
            dispatcher.dispatch(new EventDispatcherExample.ResultEvent(result));

        }

    }

    static class AsyncResultEventHandler extends AsyncChannel {

        @Override

        protected void handle(Event message) {

            EventDispatcherExample.ResultEvent resultEvent = (EventDispatcherExample.ResultEvent) message;

            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("The result is:" + resultEvent.getResult());

        }

    }
}