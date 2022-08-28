package org.pp.algorithms.datastruct;

import java.io.Serializable;
import java.util.stream.Stream;

public interface Container<E> extends Serializable {

    int size();

    boolean contains(E e);

    void clear();

    default boolean isEmpty() {
        return size() == 0;
    }

    /**
     * 流，数据源不能被修改，所以要屏蔽所有修改方法？？臃肿！
     */
    default Stream<E> stream() {
        return null;
    }
}
