package org.pp.lang;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;

public class LangInvokeTest {
    public static void main(String[] args) {

    }

    class Task implements Runnable {

        private static final MethodHandles.Lookup l = MethodHandles.lookup();
        private static final VarHandle STATE;

        static {
            try {
                STATE = l.findVarHandle(Task.class, "state", int.class);
            } catch (ReflectiveOperationException e) {
                throw new ExceptionInInitializerError(e);
            }
        }

        private volatile int state;

        public void setState() {
            STATE.compareAndSet(this, 0, 1);
            STATE.getAndAdd(this, 0, 1);
        }

        @Override
        public void run() {

        }
    }
}
