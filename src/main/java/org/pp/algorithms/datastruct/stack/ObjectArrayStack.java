package org.pp.algorithms.datastruct.stack;

import java.util.Arrays;

/**
 * ************自强不息************
 *
 * @author 鹏鹏
 * @date 2022/12/1 11:12
 * ************厚德载物************
 **/
public class ObjectArrayStack<E> implements IStack<E> {
    private final int capacity = 16;
    private int top;
    private Object[] arr = new Object[capacity];

    public static void main(String[] args) {
        ObjectArrayStack<Integer> s = new ObjectArrayStack<>();
        s.push(4);
        s.push(5);
        s.push(6);
        s.push(7);
        s.push(1);
        s.push(2);
        s.push(3);
        s.print();
        s.pop();
        s.pop();
        s.print();
        s.clear();
        s.print();
    }

    @Override
    public void push(E elem) {
        arr[top++] = elem;
    }

    @Override
    public E pop() {
        return (E) arr[--top];
    }

    @Override
    public void clear() {
        arr = Arrays.copyOf(new Object[]{}, arr.length);
    }

    @Override
    public void print() {
        System.out.println(Arrays.toString(arr));
    }
}
