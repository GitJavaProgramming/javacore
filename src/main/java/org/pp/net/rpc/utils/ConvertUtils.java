package org.pp.net.rpc.utils;

public class ConvertUtils {
    public static int byteArrayToInt(byte[] bytes) {
        int result = 0;
        for (int i = 0; i < bytes.length; i++) {
            result = result << 8 | bytes[i] & 0xff;
        }
        return result;
    }
}
