package org.pp.algorithms.datastruct.tree.hafuman;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * ************自强不息************
 *
 * @author 鹏鹏
 * @date 2022/9/4 17:18
 * ************厚德载物************
 **/
@Data
@ToString
@NoArgsConstructor
public class TreeNode {
    // 数据域
    private String data;
    // 权重
    private int weight;
    // 左孩子树
    private TreeNode left;
    // 右孩子树
    private TreeNode right;

    public TreeNode(String data, int weight) {
        this.data = data;
        this.weight = weight;
    }
}


