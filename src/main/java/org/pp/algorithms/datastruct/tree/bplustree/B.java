package org.pp.algorithms.datastruct.tree.bplustree;

public interface B {
    Object get(Comparable key);   //查询

    void remove(Comparable key);    //移除

    void insertOrUpdate(Comparable key, Object obj); //插入或者更新，如果已经存在，就更新，否则插入
}
