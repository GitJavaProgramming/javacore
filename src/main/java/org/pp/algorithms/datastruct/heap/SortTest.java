package org.pp.algorithms.datastruct.heap;

import org.junit.Test;

import java.util.PriorityQueue;

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

    @Test
    public void testPriorityQueue() {
        PriorityQueue<Integer> q = new PriorityQueue();
        q.add(4);
        q.add(1);
        q.add(3);
        q.add(2);
        q.add(16);
        q.add(9);
        q.add(10);
        q.add(14);
        q.add(8);
        q.add(7);
        System.out.println(q);
    }
}
