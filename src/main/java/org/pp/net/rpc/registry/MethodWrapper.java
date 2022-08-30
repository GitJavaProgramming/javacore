package org.pp.net.rpc.registry;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * 方法签名
 */
@Data
@ToString
public class MethodWrapper implements Serializable, Comparable<MethodWrapper> {
    /* 接口类型 */
    private Class interfaceType;
    /* 方法名 */
    private String methodName;
    /* 返回值类型 */
    private Class returnType;
    /* 参数类型数组 */
    private Class[] argsTypeArray;

    @Override
    public int compareTo(MethodWrapper o) {
        return this.toString().compareTo(o.toString());
    }

//    @Override
//    public void writeExternal(ObjectOutput out) throws IOException {
//
//    }
//
//    @Override
//    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
//
//    }
}
