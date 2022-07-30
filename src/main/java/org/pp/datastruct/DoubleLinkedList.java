package org.pp.datastruct;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Iterator;

/**
 * 双向链表 首尾都可插入元素
 */
public class DoubleLinkedList<E> extends AbstractFiniteSequence<E> implements Container<E> {

    private Node<E> head;
    private Node<E> tail;
//    private int size;

    public DoubleLinkedList() {
    }

    public DoubleLinkedList(Node<E> node) {
        this.head = node;
        this.tail = node;
        size++;
    }

    /**
     * 插入元素作为头结点
     */
    public void putFirst(E e) {
        size++;
        if (head == tail && head == null) {
            Node<E> node = new Node<>(e);
            head = node;
            tail = node;
            return;
        }
        Node<E> node = new Node<>(e);
        head.prev = node;
        node.next = head;
        head = node;
    }

    public E getFirst() {
        return (head == null) ? null : head.elem;
    }

    public void push(E e) { // jdk默认实现
        Node<E> last = tail; // 保存尾节点
        Node<E> node = new Node<>(e, last, null); // 新建结点，prev指向尾节点
        tail = node;
        if (last == null) { // 链表为空
            head = node;
        } else {
            last.next = node; // 不为空就直接作为尾节点
        }
        size++;
    }

    public E getLast() {
        return (tail == null) ? null : tail.elem;
    }

    public E poll() {
        if (head == null) {
            return null;
        }
        Node<E> next = head.next;
        E elem = head.elem;
        head.next = null;
        head.elem = null;
        if (next != null) {
            next.prev = null;
        }
        head = next;
        size--;
        return elem;
    }

    public E pop() {
        if (tail == null) {
            return null;
        }
        Node<E> node = tail.prev;
        E elem = tail.elem;
        tail.elem = null;
        tail.prev = null;
        tail = node;
        size--;
        return elem;
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    public int size() {
        return size;
    }

    public void clear() {
        while (head != null) {
            Node<E> next = head.next;
            head.elem = null;
            head.next = null;
            if (next != null) {
                next.prev = null;
            }
            head = next;
        }
        head = tail = null;
        size = 0;
    }

    public boolean contains(E e) {
        if (size == 0) {
            return false;
        }
        Node<E> curr = head;
        while (curr != null) {
            if (e == curr.elem) {
                return true;
            }
            curr = curr.next;
        }
        return false;
    }

    public E[] toArray() {
//        (E[]) Array.newInstance(E.class, size); // 编译期间泛型擦除，反射获取泛型擦出后的类型
        E[] result = (E[]) Array.newInstance(Object.class, size); // 这样可以用？ 为什么？
//        Object[] result = new Object[size];
        int i = 0;
        for (Node<E> x = head; x != null; x = x.next)
            result[i++] = x.elem;
        return result;
    }

    public DoubleLinkedList<E> sort() { // 算法优化?
        E[] array = toArray();
        Arrays.sort(array);
        this.clear();
        for (E elem : array) {
            this.push(elem);
        }
        return this;
    }

    public int index(E elem) {
        Node<E> node = head;
        int index = 0;
        while (node != null) {
            if (node.elem == elem) {
                return index;
            }
            index++;
            node = node.next;
        }
        return -1;
    }

    public DoubleLinkedList<E> subList(int begin, int end) {
        Node<E> node = skip(begin); // 包含begin
        DoubleLinkedList<E> list = new DoubleLinkedList<>();
        for (int i = begin; i < end; i++) {
            if (node == null) {
                break;
            }
            list.push(node.elem);
            node = node.next;
        }
        return list;
    }

    private Node<E> skip(int num) {
        Node<E> node = head;
        int i = 0;
        while (i < num) {
            node = node.next;
            i++;
        }
        return node;
    }

    public String toString() {
        if (size == 0) {
            return "[...]";
        }
        Node<E> curr = head;
        StringBuilder sb = new StringBuilder();
        while (curr != null) {
            sb.append(curr.elem).append(",");
            curr = curr.next;
        }
        return sb.substring(0, sb.length() - 1);
    }

    public void toTree() {

    }

    static class Node<E> {
        E elem;
        Node prev;
        Node next;

        public Node(E elem) {
            this.elem = elem;
        }

        public Node(E elem, Node prev, Node next) {
            this.elem = elem;
            this.prev = prev;
            this.next = next;
        }
    }
}
