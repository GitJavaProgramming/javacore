package org.pp.algorithms.datastruct.stack;

import java.lang.reflect.Array;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;

/**
 * ************自强不息************
 *
 * @author 鹏鹏
 * @date 2022/11/30 8:43
 * ************厚德载物************
 **/
public class ObjectStack<T> implements IStack<T> {
    private final int capacity = 5;
    private int top;
    private T[] arr;

    public ObjectStack() {
        arr = (T[]) Array.newInstance(getSuperClassGenericType(getClass(), 0), capacity);
    }

    public ObjectStack(int capacity) {
        arr = (T[]) Array.newInstance(getSuperClassGenericType(getClass(), 0), capacity);
    }

    public static void main(String[] args) {
        ObjectStack<Integer> s = new ObjectStack<>();
        s.push(1);
        s.push(2);
        s.push(3);
        s.push(4);
        s.push(5);
        s.push(6);
        s.push(7);
        s.print();
        s.pop();
        s.pop();
        s.print();
        s.clear();
        s.print();
    }

    public static Class getSuperClassGenericType(final Class clazz, final int index) {
        Type genType = clazz.getGenericSuperclass();
        if (!(genType instanceof ParameterizedType)) {
            return Object.class;
        }
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

        if (index >= params.length || index < 0) {
            return Object.class;
        }
        if (!(params[index] instanceof Class)) {
            return Object.class;
        }
        return (Class) params[index];
    }

    public void push(T elem) {
        arr = Arrays.copyOf(arr, top + 1); // 自动扩容
        arr[top++] = elem;
    }

    public T pop() {
        T elem = arr[--top];
        arr = Arrays.copyOf(arr, top);
        return elem;
    }

    public void clear() {
        top = 0;
        arr = (T[]) Array.newInstance(getSuperClassGenericType(getClass(), 0), 0);
    }

    public void print() {
        System.out.println(Arrays.toString(arr));
    }
}
