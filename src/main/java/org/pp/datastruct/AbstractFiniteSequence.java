package org.pp.datastruct;

import java.util.Iterator;

/**
 * 抽象有限序列 公共属性 公共行为？
 * 迭代--查找 比较 排序
 */
public abstract class AbstractFiniteSequence<E> implements Iterable<E> {

    protected int size; // 有限序列公共属性

    public abstract Iterator<E> iterator(); // 指定迭代默认访问序列的方式

    public int size() {
        return size;
    }

    // 其他公共行为
}
