package org.pp.datastruct;

import java.util.Comparator;

/**
 * 结点有黑红两色
 * 根结点是黑色
 * 空的叶结点是黑色
 * 如果一个结点是红色，则它的两个子节点都是黑色（即不会出现红色结点相连，RB树上红色结点挂黑结点不需要平衡化）
 * 对每个结点，从该结点到其子孙结点（包含空叶结点）的所有路径上包含相同数目的黑结点
 * 几个重要的方法：leftRotate、fixupInsertion、next
 */
public class RedBlackTree<K extends Comparable<K>, E> implements SearchTree<K, E> {

    private transient static final boolean BLACK = true;
    private transient static final boolean RED = false;
    private int size;
    private transient Node<K, E> root;
    private transient Comparator<K> comparator;

    public E findMin() {
        return getMinNode().elem;
    }

    public E findMax() {
        return getMaxNode().elem;
    }

    private Node<K, E> getMinNode() {
        return getMinNode(root);
    }

    private Node<K, E> getMaxNode() {
        return getMaxNode(root);
    }

    private Node<K, E> getMaxNode(Node<K, E> node) {
        while (node.right != null) {
            node = node.right;
        }
        return node;
    }

    private Node<K, E> getMinNode(Node<K, E> node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    public void print() {
        if (root == null) {
            System.out.println("空树.");
            return;
        }
        Node<K, E> node = getMinNode();
        while (node != null) {
            System.out.println("->" + node.elem);
            node = next(node);
        }
    }

    public Node<K, E> next(Node<K, E> curr) {
        Node<K, E> node;
        if (curr.right != null) { // 找右子树的最左子节点
            node = curr.right;
            while (node.left != null) {
                node = node.left;
            }
        } else { // 回溯
            node = curr.parent;
            if (curr == node.left) {
                return node;
            } else { // 结点在右子树，往上回溯判断结点在左右子树，左右决定大小
                Node<K, E> temp = curr;
                while (node != null) {
                    if (temp == node.right) {
                        temp = node;
                        node = node.parent;
                    } else {
                        return node;
                    }
                }
            }
        }
        return node;
    }

    @Override
    public void put(K key, E elem) {
        if (root == null) {
            root = new Node<>(key, elem, null);
            size++;
            return;
        }
        Node<K, E> t = root;
        Node<K, E> parent = t; // 要放出入的结点的父结点
        int cpr = 0; // 决定存放的位置，while语句之前定义，避免每次循环都定义
        while (t != null) {
            parent = t;
            cpr = key.compareTo(parent.key);
            if (cpr < 0) {
                t = t.left;
            } else if (cpr > 0) {
                t = t.right;
            } else {
                return; // 元素存在
            }
        }
        Node<K, E> node = new Node<>(key, elem, parent, RED); // 由于根结点是黑色，所以每次放入红色结点，否则树将变全黑！
        addNode(node, parent, cpr);
        fixupInsertion(node);
    }

    /**
     * 旋转过程以及旋转后的树违背了红黑树哪些性质
     */
    private void fixupInsertion(Node<K, E> node) {
        if (node.parent.color/*父节点是黑色*/) {
            return;
        }
        while (/*node != null && */node != root && !node.parent.color/*父结点是红色*/) {
            if (node.parent == node.parent.parent.left) { // 父结点在祖父结点的左子树
                Node<K, E> y = node.parent.parent.right;
                if (!y.color) { // 叔叔结点是红色
                    node.parent.color = BLACK;
                    y.color = BLACK;
                    node.parent.parent.color = RED;
                    node = node.parent.parent;
                } else {
                    if (node == node.parent.right) {  // 右孩子
                        node = node.parent;
                        leftRotate(node);
                    }
                    node.parent.color = BLACK;
                    node.parent.parent.color = RED;
                    rightRotate(node.parent.parent);
                }
            } else {
                Node<K, E> y = node.parent.parent.left;
                if (!y.color) { // 叔叔结点是红色
                    node.parent.color = BLACK;
                    y.color = BLACK;
                    node.parent.parent.color = RED;
                    node = node.parent.parent;
                } else {
                    if (node == node.parent.left) {
                        node = node.parent;
                        rightRotate(node);
                    }
                    node.parent.color = BLACK;
                    node.parent.parent.color = RED;
                    leftRotate(node.parent.parent);
                }
            }
        }
        root.color = BLACK;
    }

    private void rightRotate(Node<K, E> p) {
        Node<K, E> l = p.left;
        p.left = l.right;
        if (l.right != null) l.right.parent = p;
        l.parent = p.parent;
        if (p.parent == null)
            root = l;
        else if (p.parent.right == p)
            p.parent.right = l;
        else p.parent.left = l;
        l.right = p;
        p.parent = l;
    }

    /**
     * x.parent = t
     * x.left = a
     * x.right = y
     * y.parent = x
     * y.left = b
     * y.right = c
     * --->>>
     * x.parent = y
     * x.left = a
     * x.right = b
     * y.parent = t
     * y.left = x
     * y.right = c
     * 左旋结果：使y成为该子树新的根，x成为y的左孩子，y的左孩子成为x的右孩子
     */
    private void leftRotate(Node<K, E> x) {
        // 注意每个非空Node结点包含父引用和左右孩子引用，需要双向绑定
        // 先断开x和右孩子关系，此时y.parent指向t
        Node<K, E> y = x.right;
        // 1、y的左孩子成为x的右孩子，双向绑定
        x.right = y.left;
        if (y.left != null) {
            y.left.parent = x;
        }
        // 2、y成为新的根，双向绑定
        Node<K, E> t = x.parent;
        y.parent = t; // 指针上移
        if (t == null) // x是root，指针上移过程中结点可能重新着色，为了维护红黑树性质root会变，但每次root变动只会放在左右子树
            root = y;
        else if (x == t.left) // 左子树
            t.left = y;
        else // 右子树
            t.right = y;
        // 3、x成为y的左孩子
        x.parent = y;
        y.left = x;
    }

    private void addNode(Node<K, E> node, Node<K, E> parent, int select) {
        if (select < 0) {
            parent.left = node;
        }
        if (select > 0) {
            parent.right = node;
        }
        size++;
    }

    @Override
    public E removeKey(K key) {
        Node<K, E> node = getNodeByKey(key);
        return node.elem;
    }

    private Node<K, E> getNodeByKey(K key) {
        Node<K, E> node = getMinNode();
        while (node != null) {
            if (node.key.compareTo(key) == 0) {
                return node;
            }
            node = next(node);
        }
        return null;
    }

    @Override
    public boolean contains(E e) {
        Node<K, E> node = getMinNode();
        while (node != null) {
            if (node.elem == e) {
                return true;
            }
            node = next(node);
        }
        return false;
    }

    public boolean containsKey(K key) {
        Node<K, E> node = root;
        int cpr = 0;
        while (node != null) {
            cpr = key.compareTo(node.key);
            if (cpr == 0) {
                return true;
            }
            node = (cpr < 0 ? node.left : node.right);
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {

    }

    final class Node<K, E> {
        private K key;
        private E elem;
        private Node<K, E> parent;
        private Node<K, E> left;
        private Node<K, E> right;
        private boolean color = BLACK;

        public Node(K key, E elem, Node<K, E> parent) {
            this.key = key;
            this.elem = elem;
            this.parent = parent;
        }

        public Node(K key, E elem, Node<K, E> parent, boolean color) {
            this.key = key;
            this.elem = elem;
            this.parent = parent;
            this.color = color;
        }

        public Node(K key, E elem, Node<K, E> parent, Node<K, E> left, Node<K, E> right, boolean color) {
            this.key = key;
            this.elem = elem;
            this.parent = parent;
            this.left = left;
            this.right = right;
            this.color = color;
        }

        public K getKey() {
            return key;
        }

        public E getElem() {
            return elem;
        }
    }
}
