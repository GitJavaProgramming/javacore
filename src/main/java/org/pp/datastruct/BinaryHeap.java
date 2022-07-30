package org.pp.datastruct;

import java.util.Arrays;

/**
 * 堆序性质 顺序存储
 */
public class BinaryHeap<E> implements Container<E> {

    private static final int DEFAULT_SIZE = 10;
    private int size;
    private Comparable<E>[] array;

    public BinaryHeap() {
        this.array = new Comparable[DEFAULT_SIZE];
        this.size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean contains(E e) {
        return false;
    }

    @Override
    public void clear() {
        for (Comparable c : array) {
            c = null;
        }
        size = 0;
    }

    /**
     * 插入元素造成相对位置变化，所以堆排序是不稳定的
     * 这里插入元素生成最小堆
     */
    public void add(Comparable val) {
        if (size == array.length)
            expand();
        int curr = size++;
        while (curr > 0) { // 不断进行二分比较、交换，将最小值放在队列头
            int parent = (curr - 1) / 2;
            if (val.compareTo(array[parent]) > 0)
                break;
            array[curr] = array[parent];
            curr = parent;
        }
        array[curr] = val;
    }

    /**
     * 优化后的完全二叉树删除元素算法
     */
    public Comparable<E> poll() {
        check();
        Comparable<E> min = array[0];
        Comparable<E> last = array[--size]; // 缓存最后一个结点
        array[size] = null; // 删除最后一个结点
        int child = 0, curr = 0;
        while ((child = 2 * curr + 1) < size) { // 找出最后一个结点在完全二叉树中左子树中的位置
            if (child + 1 < size && array[child + 1].compareTo((E) array[child]) < 0) // 判断左右子结点大小
                child++;
            if (last.compareTo((E) array[child]) <= 0)
                break;
            array[curr] = array[child];
            curr = child;
        }
        array[curr] = last;
        return min;
    }

    public Comparable<E> peek() {
        check();
        return array[0];
    }

    private void check() {
        if (isEmpty())
            throw new IllegalStateException();
    }

    private void expand() {
        Arrays.copyOf(array, 2 * array.length);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("BinaryHeap{");
        sb.append("size=").append(size);
        sb.append(", array=").append(array == null ? "null" : Arrays.asList(array).toString());
        sb.append('}');
        return sb.toString();
    }
}
