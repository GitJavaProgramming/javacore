package org.pp.algorithms.datastruct.java;

import java.util.Iterator;

public class SingleLinkedList<E> extends AbstractFiniteSequence<E> implements Container<E> {
    private final int capacity = 16; // 指定最大容量
    //    private int size;
    private Node<E> head;
    private Iterator<E> iterator; // 自定义迭代器
    private int cursor; // 当前操作的索引，顺序遍历到的位置

    public SingleLinkedList() {
    }

    public void put(E e) {
        size++;
        if (head == null) {
            head = new Node<>(e);
            return;
        }
        Node<E> curr = head;
        while (curr.next != null) {
            curr = curr.next;
        }
        curr.next = new Node<>(e);
    }

    public E take() {
        if (head == null) {
            return null;
        }
        Node<E> next = head.next; // 先保存头结点的下一个结点
        E elem = head.elem; // 保存头结点数据
        head.elem = null; // 删除头结点
        head.next = null;
        head = next;
        size--;
        return elem;
    }

    public Node<E> getHead() {
        return head;
    }

    @Override
    public Iterator<E> iterator() {
        return (iterator != null) ? iterator : new Iterator<>() {
            int index = 0; // 从头结点开始迭代
            Node<E> next = head;

            @Override
            public boolean hasNext() {
                return index < size;
            }

            @Override
            public E next() {
                next = (index == 0) ? head : next.next;
                index++;
                return next.elem;
            }
        };
    }

    @Override
    public boolean contains(E e) {
        Node<E> node = head;
        while (node != null) {
            if (node.elem == e) {
                return true;
            }
            node = node.next;
        }
        return false;
    }

    public int size() {
        return size;
    }

    public void clear() {
        while (head != null) {
            Node<E> next = head.next;
            head.elem = null;
            head.next = null;
            head = next;
        }
        size = 0;
    }

    public String toString() {
        if (size == 0) {
            return "[]";
        }
        Node<E> curr = head;
        StringBuilder sb = new StringBuilder();
        while (curr != null) {
            sb.append(curr.elem).append(",");
            curr = curr.next;
        }
        return sb.substring(0, sb.length() - 1);
    }

    static class Node<E> {
        E elem;
        Node next;

        public Node(E elem) {
            this.elem = elem;
        }

        public Node(E elem, Node<E> next) {
            this.elem = elem;
            this.next = next;
        }
    }
}
