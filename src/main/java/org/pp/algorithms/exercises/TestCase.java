package org.pp.algorithms.exercises;

import java.util.Scanner;

/**
 * ************自强不息************
 *
 * @author 鹏鹏
 * @date 2022/12/2 19:08
 * ************厚德载物************
 **/
public class TestCase {

    public static void main(String[] args) {
        // 测试回文
//        test01();
        // 测试数字反转
//        test02();
        // 二进制转10十进制
        test03();
    }

    /**
     * 测试回文
     */
    public static void test01() {
        String str = "123456789";
        StringBuilder sb = new StringBuilder();
        palindromic(str, str, sb, 0);
    }

    /**
     * 输入一个字符串，判断是否是回文，如果不是则在字符串尾部添加字符使其变成回文
     */
    public static void palindromic(final String s/*常量字符串*/, String str, StringBuilder sb, int i) {
        char[] charArray = str.toCharArray();
        int len = charArray.length - 1;
        int size = (charArray.length + 1) / 2;
        for (int j = 0; j < size; j++) {
            // 如果不是回文，则重新添加
            if (str.charAt(j) != str.charAt(len - j)) {
                System.out.println(str + "不是回文");
                str = s;
                sb.insert(0, str.charAt(i++));
                str = str.concat(sb.toString());
                palindromic(s, str, sb, i);
                return;
            }
        }
        System.out.println(str + "是回文...");
    }

    public static void test02() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("输入一个数字[输入0结束]：");
            String line = scanner.next();
            if ("0".equals(line)) {
                break;
            }
            int num = Integer.parseInt(line);
            symmetric(num);
        }
        scanner.close();
    }

    /**
     * 判断一个数是否是回文
     *
     * @param n 输入的数
     * @return true表示是回文
     */
    public static boolean symm(int n) {
        int i = n;
        int m = 0;
        while (i > 0) {
            m = m * 10 + i % 10;
            i /= 10;
        }
        return m == n;
    }

    /**
     * 输入一个数字，将数字反转输出，可用于判断是否是回文
     *
     * @param num 输入数字
     */
    public static boolean symmetric(int num) {
        final StringBuilder sb = new StringBuilder();
        int temp = num;
        while (temp != 0) {
            sb.append(temp % 10); // 取余数
            temp /= 10; // 从低位到高位数位减一
        }
        System.out.println(sb);
        return Integer.parseInt(sb.toString()) == num;
    }

    public static void test03() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("输入一个二进制数[输入0结束]：");
            String line = scanner.next();
            if ("0".equals(line)) {
                break;
            }
            if (symm(Integer.parseInt(line))) {
                System.out.println("是回文...");
            }
            convertNumber(line);
        }
    }

    /**
     * 输入一个二进制数转为10进制
     */
    public static void convertNumber(String str) {
        char[] chars = str.toCharArray();
        int value = 0;
        int index = 0;
        for (int i = chars.length - 1; i >= 0; i--) {
            if (chars[i] == '1') {
                value += power(2, index);
            }
            index++;
        }
        System.out.println("二进制：" + str + "-> 十进制数为：" + value);
    }

    private static int power(int n, int c) {
        int newNum = 1;
        while (c-- != 0) {
            newNum *= n;
        }
        return newNum;
    }
}
