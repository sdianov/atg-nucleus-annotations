package com.github.sdianov.atgannotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Inject Nucleus component into bean property annotated with it.<br/>
 * Must be used on setter methods only.
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.METHOD)
public @interface NucleusInject {

    /**
     * Nucleus name of the component to inject<br/>
     * If omitted, and injected type is annotated with 'NucleusComponent' annotation
     * processor will use component name from this annotation.<br/>
     * If omitted and injected type is not annotated with 'NucleusComponent',
     * compilation error will occur.
     */
    String name() default "";
}
