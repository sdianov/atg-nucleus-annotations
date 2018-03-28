package com.github.sdianov.atgannotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.METHOD)
public @interface NucleusValue {

    String value() default "";

    String[] list() default {};

    MapValue[] map() default {};

    String propertyRef() default "";

    CollectionOperation operation() default CollectionOperation.SET;
}
