package com.github.sdianov.atgannotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface NucleusComponent {

    String name() default "";

    Scope scope() default Scope.UNSPECIFIED;

    String description() default "";

    String[] rawLines() default {};
}
