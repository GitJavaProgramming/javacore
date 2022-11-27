package org.pp.lang;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class JavaLangTest {

    public static void main(String[] args) {
        test01();
    }

    public static void test01() {
        final ExecutorService service = Executors.newFixedThreadPool(1);
        // 未捕获异常
        Thread.setDefaultUncaughtExceptionHandler((Thread t, Throwable e) -> {
            System.out.println("线程默认未捕获异常处理器: 捕获异常[" + e.getClass().getName() + "]->" + e.getMessage());
            service.submit(() -> System.out.println("辅助线程执行..."));
        });
        throw new RuntimeException("运行时异常...");
    }

    @Test
    public void testHashCode() {
        System.out.println("C9".hashCode()); // 2134
        System.out.println("Aw".hashCode()); // 2134
        System.out.println("wA".hashCode()); // 3754
    }

    /**
     * 测试数据类型转换，精度丢失问题
     * 整型、浮点型、字符
     */
    @Test
    public void testDataType() {
        long num = Integer.MAX_VALUE + 1;
        int a = (int) num;
        System.out.println(a);
        num = Integer.MAX_VALUE + 1L;
        a = (int) num;
        System.out.println(a);

        char[] chars = {'\u2660', '\u2665', '\u2663', '\u2666'}; // [♠, ♥, ♣, ♦]
        System.out.println(Arrays.toString(chars));
    }

    /**
     * 比较Math.floor、Math.ceil与Math.round方法的区别
     */
    @Test
    public void testMath() {
        double i = 5.5, j = 4.2;
        System.out.println(Math.floor(i)); // 向下取整
        System.out.println(Math.floor(j));
        System.out.println(Math.ceil(i));  // 向上取整
        System.out.println(Math.ceil(j));
        System.out.println(Math.round(i)); // 四舍五入
        System.out.println(Math.round(j));
    }

    /**
     * 测试Java中的引用指向问题
     */
    @Test
    public void testModifyRef() {
        // 集合引用修改
        List<Integer> listA = new ArrayList<>();
        List<Integer> listB = listA;
        listA.add(1);
        System.out.println(listA); // [1]
        System.out.println(listB); // [1]
        // 数组名是引用 固定长度 编译期确定类型 无法改动
        int[] arrA = {1, 2, 3};
        int[] arrB = arrA;
        // String
        String strA = "a";
        String strB = strA;
        strA += "b"; // 常量 + 编译期确定 参考Java语言规范 基于Java8 ch4.3.3、ch15.18.1
        System.out.println(strA); // ab
        System.out.println(strB); // a
    }
}
