package org.pp.datastruct;

public interface BinaryTree<E> extends Container<E> {
    void add(E e);

    SingleLinkedList<E> removeTree(E e);

    E findMin();

    E findMax();
}
