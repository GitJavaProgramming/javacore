package org.pp.datastruct;

/**
 * （逻辑/物理）存储位置和关键字之间的对应关系，使得每个关键字对应唯一一个存储位置。按此思想建立的表称为哈希表。
 * 为了便于查找使用链表数组简单实现
 */
public class HashList<E> implements Container<E> {

    private static final int capacity = 16; // 指定最大容量
    private static final int MIN_TREEIFY_CAPACITY = 64;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;
    private int length;
    private int count;
    private DoubleLinkedList<E>[] values;

    public HashList() {
        this.length = capacity;
        this.values = new DoubleLinkedList[capacity];
    }

    public HashList(int length) {
        this.length = length;
        this.values = new DoubleLinkedList[length];
    }

    public void put(E v) {
        if (count >= Math.round(length * DEFAULT_LOAD_FACTOR)) {
            System.out.println("超过负载限定值，元素放入失败...");
            return;
        }
        int hash = getIndex(v);
        DoubleLinkedList<E> list = values[hash];
        if (list == null) { // 索引处没有值
            values[hash] = new DoubleLinkedList<>(new DoubleLinkedList.Node<>(v));
            count++;
        } else {
            list.push(v);
            if (list.size >= MIN_TREEIFY_CAPACITY) {
                // 转换红黑树
            }
        }
    }

    public boolean contains(E v) {
        int hash = getIndex(v);
        DoubleLinkedList<E> list = values[hash];
//        list.iterator()
        return list == null ? false : list.contains(v);
    }

    @Override
    public int size() {
        return count;
    }

    @Override
    public void clear() {
        for (DoubleLinkedList<E> l : values) {
            if (l == null) {
                continue;
            }
            l.clear();
        }
    }

    public void print() {
        for (DoubleLinkedList<E> l : values) {
            if (l == null) {
                System.out.println("?");
                continue;
            }
            System.out.println(l);
        }
    }

    private int getIndex(E v) { // hash函数
//        return length - 1 - (v.hashCode() % length);
        return Math.abs(v.hashCode()) % length;
    }

}
