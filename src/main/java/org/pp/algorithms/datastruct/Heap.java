package org.pp.algorithms.datastruct;

import java.util.Arrays;

/**
 * ************自强不息************
 * 算法导论-堆排序
 * 堆可以被视为一棵完全二叉树
 *
 * @author 鹏鹏
 * @date 2022/9/2 19:07
 * ************厚德载物************
 **/
public class Heap implements Exchange {

    /**
     * 使数组中元素索引i为根的子堆保持最大堆性质
     *
     * @param arr 数组
     * @param i   数组元素索引
     */
    private void maxHeapify(Integer[] arr, int i) {
        int left = (i << 1) + 1;
        int right = (i << 1) + 2;
        // 从arr[i]、arr[left]、arr[right]中找出最大的，并将其下标存在largest中
        int largest;
        if (arr[i] == null) {
            return;
        }
        if (left < arr.length && arr[left] > arr[i]) { // 左子树比根大
            largest = left;
        } else {
            largest = i;
        }
        if (right < arr.length && arr[right] > arr[largest]) {
            largest = right;
        }
        // 如果i不是最大的，则i的某个子节点中有最大元素
        if (largest != i) {
            swap(arr, i, largest); // 交换i和largest保持堆序性质
            // 交换之后递归堆化子树
            maxHeapify(arr, largest);
        }
    }

    /**
     * 根据传入数组构建最大堆
     *
     * @param arr 传入数组
     */
    public void buildMaxHeap(Integer[] arr) {
        int heapSize = (arr.length - 1);
        for (int i = (heapSize >> 1); i >= 0; i--) {
            maxHeapify(arr, i);
        }
    }

    public void heapSort(Integer[] arr/*拷贝引用到方法栈*/) {
        buildMaxHeap(arr);
        System.out.println("构建最大堆：" + Arrays.toString(arr));
        int len = arr.length;
        Integer[] temp = new Integer[len];
        for (int i = len - 1; i >= 0; i--) {
            temp[i] = arr[0]; // 取堆顶元素
            swap(arr, 0, i);
//            System.out.println("第" + i + "次:" + Arrays.toString(arr));
//            arr = Arrays.copyOf(arr, i);
            arr[0] = Integer.MIN_VALUE; // 最小值，模拟从堆中去除
            maxHeapify(arr, 0); // 最大堆
        }
        // 拷贝元素到源数组
        for (int i = 0; i < temp.length; i++) {
            arr[i] = temp[i];
        }
        System.out.println(Arrays.toString(arr));
    }

    public Integer heapMaximum(Integer[] arr) {
        return arr[0];
    }


}
