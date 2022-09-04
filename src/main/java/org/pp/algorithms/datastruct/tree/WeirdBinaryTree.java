package org.pp.algorithms.datastruct.tree;

import org.pp.algorithms.datastruct.SingleLinkedList;

import java.io.Serializable;

public class WeirdBinaryTree<E extends Comparable<E>> implements BinaryTree<E>, Cloneable, Serializable {

    private int size;
    private BinaryTreeNode<E> root;
    private int depth; // 如何实时更新树的深度？？

    public WeirdBinaryTree() {
    }

    public WeirdBinaryTree(int depth) {
        this.depth = depth;
    }

    public WeirdBinaryTree(WeirdBinaryTree<E> tree) {
        addAll(tree);
    }

    public void clear() {
        if (root == null) {
            return;
        }
        removeTree(root, new SingleLinkedList<>());
        root = null;
        size = 0;
        depth = 0;
    }

    public void addAll(WeirdBinaryTree<E> tree) {
        addAll(tree.root);
    }

    private void addAll(BinaryTreeNode<E> node) {
        addAll(node, node.e);
    }

    private void addAll(BinaryTreeNode<E> node, E e) {
        add(e);
        if (node.left != null) {
            addAll(node.left, node.left.e);
        }
        if (node.right != null) {
            addAll(node.right, node.right.e);
        }
    }

    public void add(E e) {
        BinaryTreeNode<E> node = new BinaryTreeNode<>(e);
        if (root == null) {
            root = node;
            size++;
            return;
        }
        insert(root, node); // root != null
    }

    private void insert(BinaryTreeNode<E> node, BinaryTreeNode<E> newNode) {
        int cpr = newNode.e.compareTo(node.e);
        if (cpr < 0) {
            if (node.left == null) {
                node.left = newNode;
                size++;
            } else {
                insert(node.left, newNode);
            }
        } else {
            if (node.right == null) {
                node.right = newNode;
                size++;
            } else {
                insert(node.right, newNode);
            }
        }
    }

    /**
     * 删除树
     */
    public SingleLinkedList<E> removeTree(E e) {
        SingleLinkedList<E> list = new SingleLinkedList<>();
        if (root == null) {
            return list;
        }
        BinaryTreeNode<E> node = getNode(e);
        if (node == null) {
//            throw new NoSuchElementException();
            System.out.println("元素不存在...");
            return list;
        }
        list.put(node.e);
        removeTree(node, list);
        clearNullNode(root);
        if (node == root) { // 删除的是root
            root = null;
        }
        return list;
    }

    private BinaryTreeNode<E> getNode(E e) {
        BinaryTreeNode<E> node = root;
        while (node != null) {
            int cpr = e.compareTo(node.e);
            if (cpr < 0) {
                node = node.left;
            } else if (cpr > 0) {
                node = node.right;
            } else {
                return node;
            }
        }
        return null;
    }

    public boolean contains(E e) {
        return getNode(e) == null ? false : true;
    }

    /**
     * 删除树节点，此方法不会删除父节点对当前结点的引用
     */
    private void removeTree(BinaryTreeNode<E> node, SingleLinkedList<E> list) {
        if (node.left != null) {
            list.put(node.left.e);
            removeTree(node.left, list);
            node.left = null;
        }
        if (node.right != null) {
            list.put(node.right.e);
            removeTree(node.right, list);
            node.right = null;
        }
        node.e = null;
        size--;
    }

    private void clearNullNode(BinaryTreeNode<E> node) {
        if (node == null) {
            return;
        }
        if (node.left != null) {
            if (node.left.e == null) {
                node.left = null;
            }
            clearNullNode(node.left);
        }
        if (node.right != null) {
            if (node.right.e == null) {
                node.right = null;
            }
            clearNullNode(node.right);
        }
    }

    public void print() {
        if (root == null) {
            System.out.println("空树.");
            return;
        }
        print(root);
    }

    private void print(BinaryTreeNode<E> node) {
        System.out.println("->" + node.e);
        if (node.left != null) {
            print(node.left);
        }
        if (node.right != null) {
            print(node.right);
        }
    }

    public int depth() {
        return depth;
    }

    public int size() {
        return size;
    }

    public BinaryTreeNode<E> getRoot() {
        return root;
    }

    public E findMin() {
        BinaryTreeNode<E> node = root;
        while (node.left != null) {
            node = node.left;
        }
        return node.e;
    }

    public E findMax() {
        BinaryTreeNode<E> node = root;
        while (node.right != null) {
            node = node.right;
        }
        return node.e;
    }

    public SingleLinkedList<E> list() {
        SingleLinkedList<E> list = new SingleLinkedList<>();
        WeirdBinaryTree<E> tree = new WeirdBinaryTree<>(this);
        list.put(tree.root.e);
        tree.removeTree(tree.root, list);
        tree.root = null;
        return list;
    }

    /**
     * 左右兄弟结点表示
     */
    static class BinaryTreeNode<E> {
        E e;
        BinaryTreeNode<E> left;
        BinaryTreeNode<E> right;

        public BinaryTreeNode(E e) {
            this.e = e;
        }

        public BinaryTreeNode(E e, BinaryTreeNode<E> left, BinaryTreeNode<E> right) {
            this.e = e;
            this.left = left;
            this.right = right;
        }
    }
}
