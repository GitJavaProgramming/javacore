package org.pp.algorithms.loadbalance;

/**
 * ************自强不息************
 *
 * @author 鹏鹏
 * @date 2022/9/1 19:20
 * ************厚德载物************
 **/
public class Weight {
    // 节点信息
    private String server;
    // 节点权重值
    private Integer weight;
    // 动态权重值
    private Integer currentWeight;


    // 构造方法
    public Weight() {
    }

    public Weight(String server, Integer weight, Integer currentWeight) {
        this.server = server;
        this.weight = weight;
        this.currentWeight = currentWeight;
    }


    // 封装方法
    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getCurrentWeight() {
        return this.currentWeight;
    }

    public void setCurrentWeight(Integer currentWeight) {
        this.currentWeight = currentWeight;
    }
}
