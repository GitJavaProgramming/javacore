package org.pp.db.jdbc.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

/**
 * ************自强不息************
 *
 * @author 鹏鹏
 * @date 2022/9/16 20:05
 * ************厚德载物************
 **/
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MethodWrapper {
    private String sql;
    private String methodName;
    private Class returnType;
    private List<Obj> objList = new LinkedList<>();

    @AllArgsConstructor
    @Data
    static class Obj {
        String paramName;
        Object paramValue;
    }
}
