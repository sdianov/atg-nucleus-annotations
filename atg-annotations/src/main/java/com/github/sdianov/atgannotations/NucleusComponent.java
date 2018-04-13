package com.github.sdianov.atgannotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation processor will generate Nucleus ".property" file
 * for any class annotated with the NucleusComponent annotation.<br/>
 *
 */
@Target(value = ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface NucleusComponent {

    /**
     * Describes Nucleus component path and name<br/>
     * If set to "", the component path and name will be built
     * from the annotated class package and name.
     */
    String name() default "";

    /**
     * If set to 'true', the '.property' file will not be created
     * Useful for injecting interface types into components
     */
    boolean isInterface() default false;

    /**
     * Scope of the Nucleus component<br/>
     * Generates construction "$scope=[value]"
     */
    Scope scope() default Scope.UNSPECIFIED;

    /**
     * Description of the component
     */
    String description() default "";

    /**
     * Allows to add arbitrary lines into '.property' file.<br/>
     * These lines are placed after component header properties
     */
    String[] rawLines() default {};
}
