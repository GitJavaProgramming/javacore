package org.pp.algorithms.loadbalance;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * ************自强不息************
 *
 * @author 鹏鹏
 * @date 2022/9/1 19:13
 * ************厚德载物************
 **/
public class Servers {
    public static List<String> SERVERS = Arrays.asList(
            "44.120.110.001:8080",
            "44.120.110.002:8081",
            "44.120.110.003:8082",
            "44.120.110.004:8083",
            "44.120.110.005:8084"
    );

    public static Map<String, Integer> WEIGHT_SERVERS = new LinkedHashMap<>();

    static {
        // 权重值设置的略微小一点，方便后续理解算法
        WEIGHT_SERVERS.put("44.120.110.001:8080", 3);
        WEIGHT_SERVERS.put("44.120.110.002:8081", 2);
        WEIGHT_SERVERS.put("44.120.110.003:8082", 1);
    }
}
