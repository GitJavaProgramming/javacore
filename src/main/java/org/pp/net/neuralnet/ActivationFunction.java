package org.pp.net.neuralnet;

public interface ActivationFunction {
    //计算值
    double computeValue(double val);
    //计算导数
    double computeDerivative(double val);
}
