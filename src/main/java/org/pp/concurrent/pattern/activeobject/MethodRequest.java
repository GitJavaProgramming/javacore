package org.pp.concurrent.pattern.activeobject;

public abstract class MethodRequest {
    protected final Servant servant;
    protected final FutureResult futureResult;


    public MethodRequest(Servant servant, FutureResult futureResult) {
        this.servant = servant;
        this.futureResult = futureResult;
    }


    public abstract void execute();
}