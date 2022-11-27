package org.pp.algorithms.sort;

import org.junit.Test;

public class Client {


    /**
     * 测试进制转换  自定义实现？？
     * 找出二进制表示中最大连续出现1的串的长度
     * 将最大串打印出来  动态规划
     */
    @Test
    public void testBinaryNumber(int n) {
        String s = Integer.toBinaryString(n); // 将整数转换成二进制字符串
        /**
         * 找出二进制表示中最大连续出现1的串的长度
         */
        System.out.println(s);
        byte[] bytes = s.getBytes();
//        System.out.println(Arrays.toString(s.getBytes()));
        int count = 1;
        int max = 0;
        for (int i = 0; i < bytes.length; i++) {
            if (bytes[i] == 49) {
                if (i == bytes.length - 1) {
                    if ((n & 1) == 1) { // test 101（5） 1011（11） 1101（13） 11011（27）
                        if (max <= count) {
                            max = count++;
                        }
                    }
                    break;
                }
                if (bytes[i + 1] == 49) {
                    count++;
                } else {
                    if (max < count) {
                        max = count;
                    }
                    count = 1;
                }
            }
        }
        System.out.println(max);
    }
}
