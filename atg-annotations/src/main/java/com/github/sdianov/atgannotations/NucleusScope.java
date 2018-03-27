package com.github.sdianov.atgannotations;

public enum NucleusScope {
    UNSPECIFIED,
    GLOBAL,
    SESSION,
    REQUEST,
    PROTOTYPE,
    @Deprecated WINDOW
}
