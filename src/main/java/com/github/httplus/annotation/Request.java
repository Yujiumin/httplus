package com.github.httplus.annotation;

import java.lang.annotation.*;

@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Request {

    String value();

    String method() default "GET";

}
