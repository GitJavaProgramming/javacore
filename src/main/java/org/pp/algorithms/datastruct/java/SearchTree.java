package org.pp.algorithms.datastruct.java;

public interface SearchTree<K, E> extends Container<E> {
    void put(K key, E elem);

    E removeKey(K key);

    boolean containsKey(K key);

    E findMin();

    E findMax();
}
