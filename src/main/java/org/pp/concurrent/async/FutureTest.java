package org.pp.concurrent.async;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class FutureTest {
    public static void main(String[] args) {
        /**
         * Possible state transitions:
         * NEW -> COMPLETING -> NORMAL
         * NEW -> COMPLETING -> EXCEPTIONAL
         * NEW -> CANCELLED
         * NEW -> INTERRUPTING -> INTERRUPTED
         * COMPLETING表示任务已经执行完成了，但是共享变量和排队线程需要控制，cas保证只有一个线程胜出
         * MethodHandles.Lookup l = MethodHandles.lookup(); ...
         */
        FutureTask<Integer> futureTask = new FutureTask(() -> {
            TimeUnit.SECONDS.sleep(2);
            return 89;
        });
        IntStream.range(0, 15).mapToObj(i -> new Thread(futureTask)).forEach(Thread::start);
        try {
            futureTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
