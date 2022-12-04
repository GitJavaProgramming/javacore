package org.pp.algorithms.loadbalance;

import java.util.HashMap;
import java.util.Map;

/**
 * ************自强不息************
 *
 * @author 鹏鹏
 * @date 2022/9/1 19:19
 * ************厚德载物************
 **/
public class RoundRobinWeight {
    // 初始化存储每个节点的权重容器
    private static final Map<String, Weight> weightMap = new HashMap<>();

    // 计算总权重值，只需要计算一次，因此放在静态代码块中执行
    private static int weightTotal = 0;

    static {
        sumWeightTotal();
    }


    // 求和总权重值，后续动态伸缩节点时，再次调用该方法即可。
    public static void sumWeightTotal() {
        for (Integer weight : Servers.WEIGHT_SERVERS.values()) {
            weightTotal += weight;
        }
    }


    // 获取处理本次请求的具体服务器IP
    public static String getServer() {
        // 判断权重容器中是否有节点信息
        if (weightMap.isEmpty()) {
            // 如果没有则将配置的权重服务器列表挨个载入容器
            Servers.WEIGHT_SERVERS.forEach((servers, weight) -> {
                // 初始化时，每个节点的动态权重值都为0
                weightMap.put(servers, new Weight(servers, weight, 0));
            });
        }


        // 每次请求时，更改动态权重值
        for (Weight weight : weightMap.values()) {
            weight.setCurrentWeight(weight.getCurrentWeight()
                    + weight.getWeight());
        }


        // 判断权重容器中最大的动态权重值
        Weight maxCurrentWeight = null;
        for (Weight weight : weightMap.values()) {
            if (maxCurrentWeight == null || weight.getCurrentWeight()
                    > maxCurrentWeight.getCurrentWeight()) {
                maxCurrentWeight = weight;
            }
        }


        // 最后用最大的动态权重值减去所有节点的总权重值
        maxCurrentWeight.setCurrentWeight(maxCurrentWeight.getCurrentWeight()
                - weightTotal);


        // 返回最大的动态权重值对应的节点IP
        return maxCurrentWeight.getServer();
    }


    public static void main(String[] args) {
        // 使用for循环模拟6次请求
        for (int i = 1; i <= 6; i++) {
            System.out.println("第" + i + "个请求：" + getServer());
        }
    }
}
