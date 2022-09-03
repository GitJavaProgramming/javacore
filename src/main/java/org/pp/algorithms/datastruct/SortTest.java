package org.pp.algorithms.datastruct;

import org.junit.Test;

/**
 * ************自强不息************
 *
 * @author 鹏鹏
 * @date 2022/9/2 19:56
 * ************厚德载物************
 **/
public class SortTest {

    @Test
    public void heapSortTest() {
        Integer[] arr = new Integer[]{4, 1, 3, 2, 16, 9, 10, 14, 8, 7};
        Heap heap = new Heap();
        heap.heapSort(arr);
    }
}
