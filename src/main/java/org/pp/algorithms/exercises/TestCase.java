package org.pp.algorithms.exercises;

import org.junit.Test;

/**
 * ************自强不息************
 *
 * @author 鹏鹏
 * @date 2022/12/2 19:08
 * ************厚德载物************
 **/
public class TestCase {

    /**
     * 测试回文
     */
    @Test
    public void test01() {
        String str = "123456789";
        StringBuilder sb = new StringBuilder();
        palindromic(str, str, sb, 0);
    }

    /**
     * 输入一个字符串，判断是否是回文，如果不是则在字符串尾部添加字符使其变成回文
     */
    private void palindromic(final String s/*常量字符串*/, String str, StringBuilder sb, int i) {
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
}
