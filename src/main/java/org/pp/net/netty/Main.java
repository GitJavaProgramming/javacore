package org.pp.net.netty;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        String path = System.getProperty("java.library.path");
        Arrays.stream(path.split(";")).forEach(System.out::println);
    }
}
