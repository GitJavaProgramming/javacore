package org.pp.algorithms.datastruct.tree;

import org.pp.algorithms.datastruct.SingleLinkedList;

public class Tree<E> {

    class IndexNode<K extends Comparable<K>> {
        K key;
        IndexNode parent;
        SingleLinkedList<IndexNode> children;
        boolean leaf; // true -> children.size = 0;
    }

    class DataNode {
        E elem;
    }
}
