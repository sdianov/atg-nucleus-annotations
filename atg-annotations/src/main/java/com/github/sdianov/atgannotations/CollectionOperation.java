package com.github.sdianov.atgannotations;

/**
 * Describes operation on collection (List and Map) component properties.<br/>
 * Can be used together with 'list' and 'map' values of NucleusValue annotation only.
 */
public enum CollectionOperation {
    /**
     * Replaces values of the collection<br/>
     * Produces construction "collection=values"
     */
    SET,

    /**
     * Appends values to the collection<br/>
     * Produces construction "collection+=values"
     */
    APPEND,

    /**
     * Removes values from the collection<br/>
     * Produces construction "collection-=values"
     */
    REMOVE
}
