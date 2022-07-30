package org.pp.lang;

import org.pp.util.RandomUtil;

import java.util.*;
import java.util.stream.IntStream;

/**
 * 测试java.lang包下一些的接口
 */
public class LangTest {

    public static void main(String[] args) {
        testRandom();
    }

    public static void testLang() {
        // 当前进程
        ProcessHandle processHandle = ProcessHandle.current();
        System.out.println(processHandle.info());
        System.out.println("============================================");
        // 当前线程的栈帧
        StackWalker stackWalker = StackWalker.getInstance(EnumSet.allOf(StackWalker.Option.class));
        stackWalker.forEach(s -> {
            Class<?> clazz = s.getClass();
            System.out.println(clazz);
            Arrays.stream(clazz.getFields()).forEach(f -> f.getName());
            Arrays.stream(clazz.getDeclaredFields()).forEach(f -> f.getName());
        });
        // 未捕获异常
        Thread.setDefaultUncaughtExceptionHandler((Thread t, Throwable e) -> {
            System.out.println("线程默认未捕获异常处理器: 捕获异常[" + e.getClass().getName() + "]->" + e.getMessage());
            new Thread(() -> System.out.println("辅助线程执行...")).start(); // 异步
        });
        throw new RuntimeException("运行时异常...");
    }

    /**
     * 在某段数字区间内按指定概率生成随机数字
     * 1-100数字 1-10 10%、11-20 %80、91-100 10%
     */
    public static void testRandom() {
        int min = 1, max = 100; // 数字区间
        List<Integer> segmentList = new ArrayList<>(); // 区间分段
        segmentList.add(10);
        segmentList.add(20);
        List<Integer> percentList = new ArrayList<>(); // 每段的概率
        percentList.add(1);
        percentList.add(8);
        percentList.add(1);

        // 生成100个随机数，测试区间比例
        List<Integer> result = new ArrayList<>();
        IntStream.rangeClosed(1, 100).forEach(i -> result.add(RandomUtil.random(min, max, segmentList, percentList)));

        Collections.sort(result);
        int t = 0, count = 0, index = 0;
        for (Integer i : result) {
            if (t == segmentList.size()) {
                List<Integer> ll = result.subList(index - 1, max);
                System.out.println("size = " + ll.size() + "-" + ll);
                break;
            }
            if (i > segmentList.get(t)) {
                List<Integer> ll = result.subList(count, index);
                System.out.println("size = " + ll.size() + "-" + ll);
                count = index;
                t++;
            }
            index++;
        }
    }
}
