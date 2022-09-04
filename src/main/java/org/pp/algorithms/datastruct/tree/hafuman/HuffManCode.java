package org.pp.algorithms.datastruct.tree.hafuman;

/**
 * ************自强不息************
 *
 * @author 鹏鹏
 * @date 2022/9/4 17:20
 * ************厚德载物************
 **/

/**
 * 得到叶子结点的哈夫曼编码：由于在定义结点的时候加入了一个code的成员变量，记录了每一个结点的一个编码（0或者1）
 * 只要遍历哈夫曼树，得到每一个结点的对应的那个字符，然后拼接起来及可以得到叶子结点的编码
 */
public class HuffManCode {

    /**
     * 先序遍哈夫曼树得到叶子结点的哈夫曼编码
     *
     * @param rootNode：根节点
     */
    public static void getHuffManCode(TreeNode rootNode, String huffManCode) {

        if (rootNode.getData() != null) {
            System.out.print("数据:" + rootNode.getData() + "  " + "哈夫曼编码：" + huffManCode);
            System.out.println();
            return;
        }
        getHuffManCode(rootNode.getLeft(), huffManCode + "0");
        getHuffManCode(rootNode.getRight(), huffManCode + "1");
        return;
    }

}
