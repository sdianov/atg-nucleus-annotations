package com.github.sdianov.atgannotations.processors;

import com.github.sdianov.atgannotations.*;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.NoType;
import javax.lang.model.type.TypeKind;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

public class AnnotationUtils {

    public static final String ATG_GEN_OPTION = "atggen";

    public static final Map<Scope, String> scopeNames;

    public static final Map<Class<? extends Annotation> , SetterHandler> setterHandlers;

    public static final Map<CollectionOperation, String> operatorSymbols;
    static {

        scopeNames = new HashMap<>();
        scopeNames.put(Scope.UNSPECIFIED, null);
        scopeNames.put(Scope.GLOBAL, "global");
        scopeNames.put(Scope.SESSION, "session");
        scopeNames.put(Scope.REQUEST, "request");
        scopeNames.put(Scope.WINDOW, "window");
        scopeNames.put(Scope.PROTOTYPE, "prototype");

        operatorSymbols = new HashMap<>();
        operatorSymbols.put(CollectionOperation.APPEND, "+");
        operatorSymbols.put(CollectionOperation.REMOVE, "-");
        operatorSymbols.put(CollectionOperation.SET, "");

        setterHandlers = new HashMap<>();

        //setterHandlers.put(NucleusInject.class, new InjectSetterHandler());
        //setterHandlers.put(NucleusValue.class, new ValueSetterHandler());
    }

    // prevent from instantiation
    private AnnotationUtils() {
    }

    public static boolean isNullOrBlank(final String s) {
        return s == null || s.trim().isEmpty();
    }



}
