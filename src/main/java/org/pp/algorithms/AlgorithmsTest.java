package org.pp.algorithms;

import org.junit.Test;

/**
 * ************自强不息************
 *
 * @author 鹏鹏
 * @date 2022/9/3 19:18
 * ************厚德载物************
 **/
public class AlgorithmsTest {

    /**
     * 公鸡每只5元，母鸡每只3元，三只小鸡1元，用100元买100只鸡，问公鸡、母鸡、小鸡各多少只？
     */
    @Test
    public void test1() {
        for (int g = 0; g <= 20; g++) {//公鸡最多可买20个
            for (int m = 0; m <= 33; m++) {//母鸡最多可买33个
                int x = 100 - g - m;// 三种鸡的总数是100只
                if (((x % 3) == 0) && ((5 * g + 3 * m + x / 3) == 100)) {// 总花费为100元
                    System.out.println("公鸡为:" + g + ",母鸡为:" + m + ",小鸡为:" + x);
                }
            }
        }
    }
}
