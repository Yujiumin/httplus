package com.github.httplus.annotation;

import java.lang.annotation.*;

@Inherited
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestBody {

    String root() default "xml";

    String type() default "JSON";

}
