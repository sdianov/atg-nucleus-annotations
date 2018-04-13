package com.github.sdianov.atgannotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Inject Nucleus component into class property annotated with it.<br/>
 * Used with setter methods only.
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.METHOD)
public @interface NucleusInject {
    /**
     * Nucleus name of the component to inject
     */
    String name() default "";
}
