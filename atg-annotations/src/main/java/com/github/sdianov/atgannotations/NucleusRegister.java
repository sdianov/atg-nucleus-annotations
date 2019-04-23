package com.github.sdianov.atgannotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface NucleusRegister {

    String componentName() default "";

    String propertyName() default "";

}
