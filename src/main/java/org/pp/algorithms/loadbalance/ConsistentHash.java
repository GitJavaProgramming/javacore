package org.pp.algorithms.loadbalance;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * ************自强不息************
 *
 * @author 鹏鹏
 * @date 2022/9/1 19:14
 * ************厚德载物************
 **/
public class ConsistentHash {
    // 使用有序的红黑树结构，用于实现哈希环结构
    private static final TreeMap<Integer, String> virtualNodes = new TreeMap<>();
    // 每个真实节点的虚拟节点数量
    private static final int VIRTUAL_NODES = 160;


    static {
        // 对每个真实节点添加虚拟节点，虚拟节点会根据哈希算法进行散列
        for (String serverIP : Servers.SERVERS) {
            // 将真实节点的IP映射到哈希环上
            virtualNodes.put(getHashCode(serverIP), serverIP);
            // 根据设定的虚拟节点数量进行虚拟节点映射
            for (int i = 0; i < VIRTUAL_NODES; i++) {
                // 计算出一个虚拟节点的哈希值（只要不同即可）
                int hash = getHashCode(serverIP + i);
                // 将虚拟节点添加到哈希环结构上
                virtualNodes.put(hash, serverIP);
            }
        }
    }


    public static String getServer(String IP) {
        int hashCode = getHashCode(IP);
        // 得到大于该Hash值的子红黑树
        SortedMap<Integer, String> sortedMap = virtualNodes.tailMap(hashCode);
        // 得到该树的第一个元素，也就是最小的元素
        Integer treeNodeKey = sortedMap.firstKey();
        // 如果没有大于该元素的子树了，则取整棵树的第一个元素，相当于取哈希环中的最小元素
        if (sortedMap == null)
            treeNodeKey = virtualNodes.firstKey();
        // 返回对应的虚拟节点名称
        return virtualNodes.get(treeNodeKey);
    }


    // 哈希方法：用于计算一个IP的哈希值
    public static int getHashCode(String IP) {
        final int p = 1904390101;
        int hash = (int) 1901102097L;
        for (int i = 0; i < IP.length(); i++)
            hash = (hash ^ IP.charAt(i)) * p;
        hash += hash << 13;
        hash ^= hash >> 7;
        hash += hash << 3;
        hash ^= hash >> 17;
        hash += hash << 5;


        // 如果算出来的值为负数则取其绝对值
        if (hash < 0)
            hash = Math.abs(hash);
        return hash;
    }


    public static void main(String[] args) {
        // 用for循环模拟五个不同的IP访问
        for (int i = 1; i <= 5; i++) {
            System.out.println("第" + i + "个请求：" + getServer("192.168.12.13" + i));
        }
        System.out.println("-----------------------------");
        // 用for循环模拟三个相同的IP访问
        for (int i = 1; i <= 3; i++) {
            System.out.println("第" + i + "个请求：" + getServer("192.168.12.131"));
        }
    }
}
