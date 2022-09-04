package org.pp.algorithms.datastruct.tree.hafuman;

import java.util.ArrayList;
import java.util.List;

/**
 * ************自强不息************
 *
 * @author 鹏鹏
 * @date 2022/9/4 17:20
 * ************厚德载物************
 **/
public class HuffManTest {
    // 代码引自 https://blog.csdn.net/qq_44859843/article/details/120459412
    public static void main(String[] args) {

        List<TreeNode> treeNodes = new ArrayList<>();
        treeNodes.add(new TreeNode("A", 3));
        treeNodes.add(new TreeNode("B", 1));
        treeNodes.add(new TreeNode("C", 6));
        treeNodes.add(new TreeNode("D", 4));
        TreeNode root = HuffManTree.buildHuffManTree(treeNodes);
        HuffManCode.getHuffManCode(root, "");
    }
}
