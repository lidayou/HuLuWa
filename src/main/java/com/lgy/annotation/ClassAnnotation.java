package com.lgy.annotation;

import java.lang.annotation.*;

@Inherited
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface ClassAnnotation {
    //开发者名称
    String author() default  "lgy";
    //迭代版本
    int revision() default 1;
    //类描述
    String descrip();
}
