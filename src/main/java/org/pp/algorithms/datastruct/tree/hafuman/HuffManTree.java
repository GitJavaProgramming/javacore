package org.pp.algorithms.datastruct.tree.hafuman;

import java.util.List;

/**
 * ************自强不息************
 *
 * @author 鹏鹏
 * @date 2022/9/4 17:19
 * ************厚德载物************
 **/
public class HuffManTree {

    /**
     * 哈夫曼树的构建方法
     *
     * @param treeNodes：存放结点的List集合
     * @return 哈夫曼树构建完成的根节点
     */
    public static TreeNode buildHuffManTree(List<TreeNode> treeNodes) {

        // 判断树是否为空
        if (treeNodes.size() == 0) {
            return null;
        }

        // 循环构建树，先将所有的结点排序，然后利用贪心法构建，最后返回跟结点
        while (treeNodes.size() > 1) {
            // 将结点逆序排序
            reverse(treeNodes);
            // 构建左节点
            TreeNode left = treeNodes.get(treeNodes.size() - 1);
            // 构建右结点
            TreeNode right = treeNodes.get(treeNodes.size() - 2);
            // 得出前面两个结点的父亲结点
            TreeNode parent = new TreeNode(null, left.getWeight() + right.getWeight());
            parent.setLeft(left);
            parent.setRight(right);
            // 删除List集合中的用过的两个结点
            treeNodes.remove(left);
            treeNodes.remove(right);
            // 将生成的父亲结点放入到List集合中去
            treeNodes.add(parent);
        }
        for (TreeNode treeNode : treeNodes) {
            System.out.println(treeNode);
        }
        return treeNodes.get(0);
    }

    /**
     * 对List集合中结点，按照权值的大小排序(逆序)
     */
    public static void reverse(List<TreeNode> treeNodes) {

        int n = treeNodes.size();
        for (int i = n - 1; i > 0; i--) {
            for (int j = 0; j <= i - 1; j++) {
                if (treeNodes.get(j).getWeight() <= treeNodes.get(j + 1).getWeight()) {
                    TreeNode temp = treeNodes.get(j);
                    treeNodes.set(j, treeNodes.get(j + 1));
                    treeNodes.set(j + 1, temp);
                }
            }
        }
    }
}
