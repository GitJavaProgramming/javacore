package org.pp.net.neuralnet;

public class DoubleUtil {

    private static final Double MAX_ERROR = 0.0001;

    public static boolean equals(Double a, Double b) {
        return Math.abs(a - b)< MAX_ERROR;
    }

    public static boolean equals(Double a, Double b,Double maxError) {
        return Math.abs(a - b)< maxError;
    }
}
