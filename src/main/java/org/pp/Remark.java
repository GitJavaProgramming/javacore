package org.pp;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ************自强不息************
 *
 * @author 鹏鹏
 * @date 2022/11/26 18:00
 * ************厚德载物************
 **/
@Target({ElementType.PACKAGE, ElementType.TYPE})
@Retention(RetentionPolicy.SOURCE)
public @interface Remark {
    String value() default "备注";
}
