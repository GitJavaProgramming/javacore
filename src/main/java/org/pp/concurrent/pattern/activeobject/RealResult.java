package org.pp.concurrent.pattern.activeobject;

public class RealResult implements Result {
    private final Object result;

    public RealResult(Object reslut) {
        this.result = reslut;
    }

    @Override
    public Object getResultValue() {
        return result;
    }
}