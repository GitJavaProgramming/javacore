package org.pp.algorithms.datastruct.java;

import java.util.Iterator;

public class ListIter<E> implements Iterator<E> {

    private SingleLinkedList<E> linkedList;
    private int index = 0; // 从头结点开始迭代
    private SingleLinkedList.Node<E> next; // Node必须静态，否则无法外部访问

    public ListIter(SingleLinkedList<E> linkedList) {
        this.linkedList = linkedList;
    }

    @Override
    public boolean hasNext() {
        return linkedList.size() != index;
    }

    @Override
    public E next() {
        next = (index == 0) ? linkedList.getHead() : next.next;
        index++;
        return next.elem;
    }
}
