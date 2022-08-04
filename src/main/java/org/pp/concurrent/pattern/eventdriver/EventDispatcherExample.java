package org.pp.concurrent.pattern.eventdriver;

public class EventDispatcherExample {
    static class InputEvent extends Event {
        private final int x;
        private final int y;

        public InputEvent(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }

    static class ResultEvent extends Event {
        private final int result;

        public ResultEvent(int result) {
            this.result = result;
        }

        public int getResult() {
            return result;
        }
    }

    static class ResultEventHandler implements Channel<ResultEvent> {
        @Override
        public void dispatch(ResultEvent message) {
            System.out.println("结果：" + message.getResult());
        }
    }
    static class InputEventHandler implements Channel<InputEvent> {
        private final SyncEventDispatcher dispatcher;

        public InputEventHandler(SyncEventDispatcher dispatcher) {
            this.dispatcher = dispatcher;
        }

        @Override
        public void dispatch(InputEvent message) {
            System.out.printf("X:%d,y:%d\n", message.getX(), message.getY());
            int result = message.getX() + message.getY();
            dispatcher.dispatch(new ResultEvent(result));
        }
    }
}