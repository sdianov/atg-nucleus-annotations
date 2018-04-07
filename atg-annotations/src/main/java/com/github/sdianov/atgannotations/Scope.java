package com.github.sdianov.atgannotations;

/**
 * Represents available ATG Nucleus components scopes
 * <p>UNSPECIFIED - does not append any <b>$scope</b> value to the resulting property file</p>
 */
public enum Scope {
    UNSPECIFIED,
    GLOBAL,
    SESSION,
    REQUEST,
    PROTOTYPE,
    WINDOW
}
