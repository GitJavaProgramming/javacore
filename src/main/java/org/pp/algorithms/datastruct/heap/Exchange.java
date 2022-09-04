package org.pp.algorithms.datastruct.heap;

/**
 * ************自强不息************
 *
 * @author 鹏鹏
 * @date 2022/9/2 19:16
 * ************厚德载物************
 **/
public interface Exchange {
    default <T extends Comparable<T>> void swap(T[] arr, int i, int j) {
        T temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
