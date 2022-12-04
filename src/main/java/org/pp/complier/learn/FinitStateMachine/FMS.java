package org.pp.complier.learn.FinitStateMachine;

public interface FMS {
    int STATE_FAILURE = -1;

    int yy_next(int state, byte c);

    boolean isAcceptState(int state);
}
