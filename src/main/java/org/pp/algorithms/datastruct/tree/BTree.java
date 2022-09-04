package org.pp.algorithms.datastruct.tree;

import org.pp.algorithms.datastruct.Container;
import org.pp.algorithms.datastruct.KeyHolder;

import java.util.Arrays;

/**
 * 结点包含关键字列表
 * 3种结点：根结点、内结点、叶子结点（外部结点）
 * B树--每个结点能包含的关键字有一个范围[a,b],b的最大值为2a,根结点范围[2,b],各关键字对存储在各子树中的关键字加以分隔。
 * 内结点至少a-1个关键字,至少a个子结点,最多2a-1个关键字,最多2a个子结点。
 * 如果一个内结点有2a-1个关键字则说明该结点是满容量的，B*树要求每个内结点至少包含(2/3)*b关键字以及同层内节点兄弟结点指针
 * B+树--叶子结点保存所有数据和关键字，非叶子结点只包含关键字和下一个磁盘页的指针
 * 原文：https://blog.csdn.net/u014106644/article/details/90174332
 */
public class BTree<K extends Comparable<K>/*关键字*/, E extends KeyHolder<K>/*附属数据*/> implements Container<E> {

    private static final transient int lower = 2; // 指定树中结点最少子结点数（不包含根结点），limit决定了树的存储容量，结点最多lower * 2个子结点
    private static final transient int upper = lower << 1; // 最多子结点数
    private transient int size;
    private Node root = new Node(0);
    private int height;

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean contains(E e) {
        return get(e.getKey()) != null;
    }

    @Override
    public void clear() {
        size = 0;
    }

    public Object get(K key) {
        return search(root, key, 0);
    }

    private Object search(Node x, K key, int ht) {
        Entry[] children = x.children;
        //外部节点  这里使用顺序查找
        //如果M很大  可以改成二分查找
        if (ht == 0) {
            for (int j = 0; j < x.m; j++) {
                if (equal(key, x.children[j].key))
                    return children[j].value;
            }
        }
        //内部节点  寻找下一个节点
        else {
            for (int j = 0; j < x.m; j++) {
                //最后一个节点  或者 插入值小于某个孩子节点值
                if (j + 1 == x.m || less(key, x.children[j + 1].key))
                    return search(x.children[j].next, key, ht - 1);
            }
        }
        return null;
    }

    public void put(K key, E val) {
        //插入后的节点  如果节点分裂，则返回分裂后产生的新节点
        Node u = insert(root, key, val, height);
        size++;
        //根节点没有分裂  直接返回
        if (u == null) return;
        //分裂根节点
        Node t = new Node(2);
        //旧根节点第一个孩子   新分裂节点
        t.children[0] = new Entry(root.children[0].key, null, root);
        t.children[1] = new Entry(u.children[0].key, null, u);
        root = t;
        height++;
        System.out.println("=================================================================");
    }

    private Node insert(Node h, K key, E val, int ht) {
        int j;
        //新建待插入数据数据项
        Entry t = new Entry(key, val, null);
        // 外部节点  找到带插入的节点位置j
        if (ht == 0) {
            for (j = 0; j < h.m; j++) {
                if (less(key, h.children[j].key))
                    break;
            }
        } else {
            //内部节点  找到合适的分支节点
            for (j = 0; j < h.m; j++) {
                if (j + 1 == h.m || less(key, h.children[j + 1].key)) {
                    Node u = insert(h.children[j++].next, key, val, ht - 1);
                    if (u == null) return null;
                    t.key = u.children[0].key;
                    t.next = u;
                    break;
                }
            }
        }
        //System.out.println(j + t.toString());
        //j为带插入位置，将顺序数组j位置以后后移一位 将t插入
        for (int i = h.m; i > j; i++) {
            h.children[i] = h.children[i - 1];
        }
        System.out.println(j + t.toString());
        h.children[j] = t;
        h.m++;
        if (h.m < upper/*留出一个空位，移位时不会数组越界*/) return null;
            //如果节点已满  则执行分类操作
        else return split(h);
    }

    private Node split(Node h) {
        //将已满节点h的后一般M/2节点分裂到新节点并返回
        Node t = new Node(lower);
        h.m = lower;
        for (int j = 0; j < lower; j++) {
            t.children[j] = h.children[lower + j];
            h.children[lower + j] = null;
        }
        return t;
    }

    private boolean less(Comparable key, Comparable key1) {
        return key1.compareTo(key1) < 0;
    }

    private boolean equal(Comparable key1, Comparable key2) {
        return key1.compareTo(key2) == 0;
    }

    private final class Node {
        private final Entry[] children = new Entry[upper]; // 事先分配好空间
        private int m; // 结点中的关键字数

        private Node(int k) {
            this.m = k;
        }

        @Override
        public String toString() {
            final StringBuffer sb = new StringBuffer("Node{");
            sb.append("children=").append(children == null ? "null" : Arrays.asList(children).toString());
            sb.append(", m=").append(m);
            sb.append('}');
            return sb.toString();
        }
    }

    private final class Entry<K extends Comparable<K>> {
        K key;
        E value;
        Node next;

        public Entry(K key, E value, Node next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }

        @Override
        public String toString() {
            final StringBuffer sb = new StringBuffer("Entry{");
            sb.append("key=").append(key);
            sb.append(", value=").append(value);
            sb.append(", next=").append(next);
            sb.append('}');
            return sb.toString();
        }
    }
}
