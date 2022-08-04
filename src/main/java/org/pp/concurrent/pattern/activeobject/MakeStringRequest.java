package org.pp.concurrent.pattern.activeobject;

public class MakeStringRequest extends MethodRequest {
    private final int count;
    private final char fillchar;

    public MakeStringRequest(Servant servant, FutureResult futureResult, int count, char fillChar) {
        super(servant, futureResult);
        this.count = count;
        this.fillchar = fillChar;
    }

    @Override
    public void execute() {
        Result result = servant.makeString(count, fillchar);
        futureResult.setResult(result);
    }
}