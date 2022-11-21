package org.pp.db.jdbc.core;

import java.util.StringTokenizer;

/**
 * ************自强不息************
 *
 * @author 鹏鹏
 * @date 2022/9/16 21:17
 * ************厚德载物************
 **/
public class SqlParser {

    public static String parse(MethodWrapper methodWrapper) {
        StringBuilder sb = new StringBuilder();
        StringTokenizer tokenizer = new StringTokenizer(methodWrapper.getSql(), "#");
        while (tokenizer.hasMoreTokens()) {
            String element = tokenizer.nextToken();
            if (element.startsWith("{") && element.endsWith("}")) {
                element = element.substring(1, element.length() - 1).trim();
                sb.append(" ? ");
            } else {
                sb.append(element);
            }
        }
        return sb.toString();
    }
}
