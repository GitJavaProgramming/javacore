package org.pp.db.jdbc.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ************自强不息************
 *
 * @author 鹏鹏
 * @date 2022/9/16 19:52
 * ************厚德载物************
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Select {
    String sql() default "";
}
