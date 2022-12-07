package org.pp.algorithms.datastruct.tree;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * ************自强不息************
 *
 * @author 鹏鹏
 * @date 2022/12/6 18:42
 * ************厚德载物************
 **/
public class MyBinaryTree<E> {

    private Node root;
    private int size;

    public static void main(String[] args) {
        MyBinaryTree<Integer> binaryTree = new MyBinaryTree<>();
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        Node node5 = new Node(5);
        Node node6 = new Node(6);
        binaryTree.root = node1;
        binaryTree.root.setLeft(node2).setRight(node3);
        node2.setLeft(node4).setRight(node5);
        node3.setLeft(node6);
        node1.preOrder();
    }

    @Accessors(chain = true)
    @AllArgsConstructor
    @Data
    static class Node<E> {
        private E elem;
        private Node left;
        private Node right;

        public Node(E elem) {
            this.elem = elem;
        }

        public String toString() {
            return elem.toString();
        }

        public void preOrder() {
            System.out.println(this);
            if (this.left != null) {
                left.preOrder();
            }
            if (this.right != null) {
                right.preOrder();
            }
        }

        public void infixOrder() {
            if (this.left != null) {
                left.infixOrder();
            }
            System.out.println(this);
            if (this.right != null) {
                right.infixOrder();
            }
        }

        public void postOrder() {
            if (this.left != null) {
                left.postOrder();
            }
            if (this.right != null) {
                right.postOrder();
            }
            System.out.println(this);
        }

    }
}

