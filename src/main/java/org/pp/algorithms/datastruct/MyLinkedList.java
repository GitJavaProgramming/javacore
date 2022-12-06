package org.pp.algorithms.datastruct;

/**
 * 单链表
 * 链表类：头指针、尾指针、链表大小
 *
 * @param <E>
 */
public class MyLinkedList<E> implements Container<E> {
    private Node<E> head = new Node<>(null);
    private Node<E> tail = head;
    private int size;

    public static void main(String[] args) {
        MyLinkedList<Integer> list = new MyLinkedList<>();
        // 向单链表中插入元素
        list.addLast(1);
        list.addLast(2);
        list.addLast(3);
        list.addLast(4);
        list.addLast(5);
        list.addLast(6);
        list.addLast(7);
        System.out.println(list);
        // 移除第一个元素
        list.removeFirst();
        list.removeFirst();
        list.addFirst(2);
        System.out.println(list);
        // 移除最后一个元素
        list.removeLast();
        list.removeLast();
        list.addFirst(1);
        list.addLast(6);
        System.out.println(list);
        // 清空链表
        list.clear();
        System.out.println(list);
        list.addFirst(1);
        System.out.println(list);
    }

    public void addFirst(E e) {
        Node<E> node = new Node<>(e);
        node.next = head.next;
        head.next = node;
        size++;
    }

    /**
     * 向链表尾部添加元素
     *
     * @param e 要添加的元素
     */
    public void addLast(E e) {
        if (e == null) {
            throw new NullPointerException();
        }
        size++;
        Node<E> newTail = new Node<>(e);
        tail.next = newTail;
        tail = newTail;
    }

    /**
     * 移除链表最后一个元素
     */
    public void removeLast() {
        if (isEmpty()) {
            return;
        }
        size--;
        Node<E> temp = head;
        while (temp.next.next != null) {
            temp = temp.next;
        }
        temp.next.elem = null; // 删除最后一个元素
        temp.next = null; // 修改倒数第二个元素指针指向空
        tail = temp;
    }

    /**
     * 移除链表第一个元素
     */
    public void removeFirst() {
        if (isEmpty()) {
            return;
        }
        size--;
        head.next.elem = null;
        head = head.next;
    }

    @Override
    public int size() {
        return size;
    }

    private Node<E> getNode(E e) {
        Node<E> temp = head;
        while (temp.next != null) {
            temp = temp.next;
            if (e.equals(temp.elem)) {
                return temp;
            }
        }
        return null;
    }

    @Override
    public boolean contains(E e) {
        Node<E> temp = head;
        while (temp.next != null) {
            temp = temp.next;
            if (e.equals(temp.elem)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void clear() {
        head.next = null;
        tail = head;
        size = 0;
    }

    @Override
    public String toString() {
        if (isEmpty()) {
            return "[]";
        }
        final StringBuffer sb = new StringBuffer();
        Node<E> temp = head;
        while (temp.next != null) {
            temp = temp.next;
            sb.append(",").append(temp.elem);
        }
        sb.insert(1, "[").append("]");
        return sb.substring(1);
    }

    private static class Node<E> {
        E elem;
        transient Node next;

        public Node(E elem) {
            this.elem = elem;
        }

        public Node(E elem, Node<E> next) {
            this.elem = elem;
            this.next = next;
        }

        @Override
        public String toString() {
            return elem.toString();
        }
    }
}
