package com.github.sdianov.atgannotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Sets value to the Nucleus component property<br/>
 * Can be used with setter methods only<br/>
 * Only one of 'value', 'list', 'map', 'propertyRef' properties can be used
 * in a single annotation.
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.METHOD)
public @interface NucleusValue {

    /**
     * Simple string value for property<br/>
     */
    String value() default "";

    /**
     * Used with List and Array property types.<br/>
     */
    String[] list() default {};

    /**
     * Used with Map property types
     */
    MapValue[] map() default {};

    /**
     * Generates Nucleus reference to another component property<br/>
     * Produces construction: "property^=[propertyRef]"
     */
    String propertyRef() default "";

    /**
     * Operation for 'list' and 'map' annotation values
     */
    CollectionOperation operation() default CollectionOperation.SET;
}
