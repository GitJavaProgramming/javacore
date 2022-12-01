package org.pp.lang;

import org.junit.Test;

import java.util.Arrays;

public class JavaLangTest {

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
}
