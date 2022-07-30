package org.pp.lang.oop;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.SOURCE/*只保留在源码中,运行时不可用*/)
@Target({ElementType.PACKAGE})
public @interface OOP {
    String value() default "面向对象设计的包标识";
}
