package org.pp.algorithms.sort;

import java.util.Arrays;

/**
 * 复制、移动、交换与稳定性
 */
public final class Sorts {
    public static void sort(int[] arr, SortType type) {
        switch (type) {
            case QUICK:
                quickSort(arr);
            case SELECT:
                selectionSort(arr);
            case INSERTION:
                insertionSort(arr);
        }
    }

    private static void printArray(int[] arr) {
        System.out.println(Arrays.toString(arr));
    }

    private static void swap(int[] arr, int m1, int m2) {
        int temp = arr[m1];
        arr[m1] = arr[m2];
        arr[m2] = temp;
    }

    /**
     * 一种划分算法：对于数组A[p...r]总是取A[r]作为主元进行划分
     * 其他划分：Hoare划分、三数取中（随机主元的变式）
     */
    private static int partition(int[] arr, int p, int r) {
        int x = arr[r]; // 选择数组最后一个元素作为主元进行比较，
        int i = p - 1;
        for (int j = p; j < r; j++) { // 一次遍历会近似排序，分成较小无序和较大的无序两个数组
            if (arr[j] <= x) { // 和主元比较，相等也交换-->减小问题规模(交换之后就不会再比较)
                i++; // 较小元素数组的尾部指针，使用指针不需要额外栈空间存放较小元素数组，只需要交换
                swap(arr, i, j); // 元素交换，较小元素
                // 没打印一次就意味着发生一次元素交换
                System.out.println(Arrays.toString(arr));
            }
        }
        swap(arr, i + 1, r); // 找到主元，一次划分完成
        return i + 1;
    }

    private static void quickSort(int[] arr, int p, int r) {
        if (p < r) {
            int q = partition(arr, p, r);
            System.out.println("基准点位置：" + q + " 划分后：" + Arrays.toString(arr));
            quickSort(arr, p, q - 1);
            quickSort(arr, q + 1, r);
        }
    }

    public static void quickSort(int[] arr) {
        quickSort(arr, 0, arr.length - 1);
    }

    /**
     * 插入排序：数组分已排未排，将未排部分插入已排部分
     */
    public static void insertionSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) { // 未排序数组
            int t = arr[i]; // 要插入的值
            int m = i; // 已排序数组的长度
            // 进入循环有两个条件：1、已排序数组没有遍历完 2、没有找到插入位置
            while (m > 0 && t < arr[m - 1]/*从已排序数组最后一个开始比*/) {
                /** // 2
                 //                if (t >= arr[m - 1]) {
                 //                    break;
                 //                }
                 */
                arr[m] = arr[m - 1]; // 复制 向前比较
                m--;
                /** // 1
                 //                if (t < arr[m - 1]) {
                 //                    arr[m] = arr[m - 1];
                 //                    m--;
                 //                }
                 */
            }
            arr[m] = t; // 找到插入位置，替换值
        }
    }

    /**
     * 每一次从待排序的数据元素中选出最小（或最大）的一个元素，存放在序列的起始位置，
     * 然后，再从剩余未排序元素中继续寻找最小（大）元素，然后放到已排序序列的末尾。
     * 以此类推，直到全部待排序的数据元素排完。
     */
    public static void selectionSort(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            int p = i; // 已排序序列末尾指针
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[p]) { // 未排序数组中找到最小值
                    p = j;
                }
            }
            if (p != i) {
                swap(arr, i, p); // 将最小值存放在未排序序列起始位置 不稳定
            }
            printArray(arr);
        }
    }

    enum SortType {
        SELECT, INSERTION, QUICK;
    }
}
