package org.pp.datastruct;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Iterator;
import java.util.PriorityQueue;

public class Client {

    public static void main(String[] args) {
//        new Client().testSLL(); // 单链表
//        new Client().testDLL(); // 双链表 双端双向 栈 队列
//        new Client().testIter(); // 迭代器
//        new Client().testArray(); // 数组
//        new Client().testHash(); // 哈希表
//        new Client().testBinaryTree(); // 二叉树  非递归遍历呢？
//        new Client().testTreeMap(); // 红黑树  链式存储结构
        new Client().testPriorityQueue(); // 优先队列 顺序存储结构
//        new Client().testBTree(); // B树 B+树 B*树
    }

    public void testBTree() {
        class Person implements KeyHolder<Integer> {
            Integer key;
            String name;
            Integer age;

            public Person(Integer key, String name, Integer age) {
                this.key = key;
                this.name = name;
                this.age = age;
            }

            public Integer getKey() {
                return key;
            }

            public void setKey(Integer key) {
                this.key = key;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public Integer getAge() {
                return age;
            }

            public void setAge(Integer age) {
                this.age = age;
            }

            @Override
            public String toString() {
                final StringBuffer sb = new StringBuffer("Person{");
                sb.append("key=").append(key);
                sb.append(", name='").append(name).append('\'');
                sb.append(", age=").append(age);
                sb.append('}');
                return sb.toString();
            }
        }
        BTree<Integer, Person> btree = new BTree<>();
        for (int i = 1; i <= 20; i++) {
            btree.put(i, new Person(i, i + "", i));
        }
        System.out.println(btree);
    }

    public void testPriorityQueue() {
        BinaryHeap<Integer> priorityQueue = new BinaryHeap<>();
        priorityQueue.add(11);
        priorityQueue.add(2);
        priorityQueue.add(14);
        priorityQueue.add(1);
        priorityQueue.add(7);
        priorityQueue.add(5);
        priorityQueue.add(8);
        priorityQueue.add(15);
        priorityQueue.add(4);
        System.out.println(priorityQueue); // [1, 2, 5, 4, 7, 14, 8, 15, 11]
        priorityQueue.poll();
        System.out.println(priorityQueue); // [2, 4, 5, 11, 7, 14, 8, 15]
    }

    public void testTreeMap() {
//        TreeMap treeMap = new TreeMap();
//        treeMap.put(new Object(), new Object());
//        System.out.println(treeMap); // java.lang.ClassCastException
//        TreeMap<Integer, Integer> treeMap = new TreeMap();
        RedBlackTree<Integer, Integer> treeMap = new RedBlackTree<>(); // key不重复 values是集合
        treeMap.put(11, 11);
        treeMap.put(2, 2);
        treeMap.put(14, 14);
        System.out.println("==================");
        treeMap.put(1, 1); // 黑黑黑红
        treeMap.put(7, 7);
        treeMap.put(5, 5);
        treeMap.put(8, 8);
        treeMap.put(15, 15);
        System.out.println("==================");
        treeMap.put(4, 4);
        System.out.println("==================");
        treeMap.print();
        System.out.println(treeMap.findMin());
        System.out.println(treeMap.findMax());
        System.out.println(treeMap.containsKey(8));
    }

    public void testBinaryTree() {
        WeirdBinaryTree<Integer> tree = new WeirdBinaryTree<>();
        tree.add(5);
        tree.add(2);
        tree.add(1);
        tree.add(6);
        tree.add(3);
        tree.add(4);
        tree.add(7);
        tree.print();
        System.out.println(tree.list());
//        System.out.println(tree.depth());
        System.out.println(tree.size());
        System.out.println(tree.findMin());
        System.out.println(tree.findMax());
        SingleLinkedList<Integer> temp = tree.removeTree(1);
        if (temp.size() != 0) {
            System.out.println("remove = " + temp);
        }
        System.out.println(tree.size());
        tree.print();
        tree.removeTree(7);
        tree.removeTree(2);
        System.out.println(tree.size());
        tree.print();
        tree.clear();
        tree.print();
    }

    public void testHashList() {
        HashList<Integer> hashList = new HashList<>();
        hashList.put(9);
        hashList.put(1);
        hashList.put(8);
        hashList.put(2);
        hashList.put(7);
        hashList.put(3);
        hashList.put(6);
        hashList.put(4);
        hashList.put(5);
        hashList.put(5);
        hashList.put(5);
        hashList.put(0);
        hashList.put(0);
        hashList.put(10);
        hashList.put(11);
        hashList.put(12);
        hashList.put(13);
        // 如果放入13次整数0呢 哈希表将直接变成链表
        System.out.println(hashList.contains(10));
        hashList.print();
    }

    public void testArray() {
        // newInstance返回Object，因为不知道具体传入的componentType占用字节数
        Integer[] array = (Integer[]) Array.newInstance(Integer.class, 10);
        Array.set(array, 0, 0);
        Array.set(array, 1, 1);
        System.out.println(Arrays.toString(array));
    }

    public void testIter() {
        SingleLinkedList<Integer> linkedList = new SingleLinkedList<>();
        linkedList.put(1);
        linkedList.put(3);
        linkedList.put(2);
        linkedList.put(4);
        Iterator<Integer> iter = linkedList.iterator();
//        Iterator<Integer> iter = new ListIter<>(linkedList);
        int sum = 0;
        while (iter.hasNext()) {
            Integer i = iter.next();
            sum += i;
        }
        System.out.println(sum);
    }

    public void testDLL() {
        DoubleLinkedList<Integer> dll = new DoubleLinkedList<>();
        dll.push(9);
        dll.push(7);
        dll.push(4);
        dll.push(2);
        System.out.println(Arrays.toString(dll.toArray()));
        System.out.println(dll.contains(7));
        System.out.println(dll.contains(6));
        System.out.println(dll.size());
        System.out.println(dll);
        System.out.println(dll.getFirst());
        System.out.println(dll.poll());
        System.out.println(dll.poll());
        System.out.println(dll);
        System.out.println(dll.getFirst());
        System.out.println(dll.getLast());
        System.out.println("========================");
        System.out.println(dll.pop());
        System.out.println(dll.pop());
        System.out.println("========================");
        System.out.println(dll);
        dll.clear();
        System.out.println(dll.getLast());
    }

    public void testSLL() {
        SingleLinkedList<Integer> linkedList = new SingleLinkedList<>();
        linkedList.put(9);
        linkedList.put(7);
        linkedList.put(8);
        linkedList.put(2);
        System.out.println(linkedList.size());
        System.out.println(linkedList);
        System.out.println(linkedList.take());
        System.out.println(linkedList.take());
        System.out.println(linkedList.size());
        linkedList.clear();
        System.out.println(linkedList);
    }
}
