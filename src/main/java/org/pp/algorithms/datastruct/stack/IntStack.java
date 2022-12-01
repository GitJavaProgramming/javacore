package org.pp.algorithms.datastruct.stack;

import java.util.Arrays;

/**
 * ************自强不息************
 *
 * @author 鹏鹏
 * @date 2022/11/30 8:43
 * ************厚德载物************
 **/
public class IntStack {
    private int capacity = 5;
    private int top;
    private int[] arr = new int[capacity];

    public IntStack() {
    }

    public IntStack(int capacity) {
        this.capacity = capacity;
    }

    public static void main(String[] args) {
        IntStack s = new IntStack();
        s.push(10);
        s.push(10);
        s.push(10);
        s.push(4);
        s.push(3);
        s.push(2);
        s.push(1);
        s.print();
        s.pop();
        s.pop();
        s.print();
        s.clear();
        s.print();
    }

    public void push(int elem) {
        arr = Arrays.copyOf(arr, top + 1); // 自动扩容
        arr[top++] = elem;
    }

    public int pop() {
        int elem = arr[--top];
        arr = Arrays.copyOf(arr, top);
        return elem;
    }

    public void clear() {
        top = 0;
        arr = new int[0];
    }

    public void print() {
        System.out.println(Arrays.toString(arr));
    }
}
