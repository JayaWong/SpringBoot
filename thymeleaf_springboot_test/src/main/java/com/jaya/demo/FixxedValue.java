package com.jaya.demo;

import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexed
public @interface FixxedValue {
    String value() default "fixxedValue";
}
