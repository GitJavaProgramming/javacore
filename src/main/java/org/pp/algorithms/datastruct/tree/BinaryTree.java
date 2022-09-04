package org.pp.algorithms.datastruct.tree;

import org.pp.algorithms.datastruct.Container;
import org.pp.algorithms.datastruct.SingleLinkedList;

public interface BinaryTree<E> extends Container<E> {
    void add(E e);

    SingleLinkedList<E> removeTree(E e);

    E findMin();

    E findMax();
}
