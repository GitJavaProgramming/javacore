package org.pp.algorithms.datastruct;

import java.io.Serializable;
import java.util.stream.Stream;

public interface Container<E> extends Serializable {

    /**
     * 求容器大小
     */
    int size();

    /**
     * 容器是否包含元素
     *
     * @param e 要判断的元素
     * @return true表示容器中存在该元素
     */
    boolean contains(E e);

    /**
     * 清空容器
     */
    void clear();

    /**
     * 容器是否为空
     *
     * @return true表示为空
     */
    default boolean isEmpty() {
        return size() == 0;
    }

    /**
     * 流，数据源不能被修改，所以要屏蔽所有修改方法？？臃肿！
     */
    default Stream<E> stream() {
        throw new UnsupportedOperationException();
    }
}
