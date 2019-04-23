package com.github.sdianov.atgannotations.processors;

import com.github.sdianov.atgannotations.CollectionOperation;
import com.github.sdianov.atgannotations.Scope;
import java.util.HashMap;
import java.util.Map;

public class AnnotationUtils {

    public static final String ATG_GEN_OPTION = "atggendir";

    public static final Map<Scope, String> scopeNames;

    public static final Map<CollectionOperation, String> operatorSymbols;

    static {

        scopeNames = new HashMap<Scope, String>();
        scopeNames.put(Scope.UNSPECIFIED, null);
        scopeNames.put(Scope.GLOBAL, "global");
        scopeNames.put(Scope.SESSION, "session");
        scopeNames.put(Scope.REQUEST, "request");
        scopeNames.put(Scope.WINDOW, "window");
        scopeNames.put(Scope.PROTOTYPE, "prototype");

        operatorSymbols = new HashMap<CollectionOperation, String>();
        operatorSymbols.put(CollectionOperation.APPEND, "+");
        operatorSymbols.put(CollectionOperation.REMOVE, "-");
        operatorSymbols.put(CollectionOperation.SET, "");

    }

    // prevent from instantiation
    private AnnotationUtils() {
    }

    public static boolean isNullOrBlank(final String s) {
        return s == null || s.trim().isEmpty();
    }


}
