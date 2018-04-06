package com.github.sdianov.atgannotations.processors;

import com.github.sdianov.atgannotations.NucleusComponent;
import com.github.sdianov.atgannotations.Scope;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.NoType;
import javax.lang.model.type.TypeKind;
import java.util.HashMap;
import java.util.Map;

public class AnnotationUtils {

    public static final String ATG_GEN_OPTION = "atggen";

    public static final Map<Scope, String> scopeNames;

    static {

        scopeNames = new HashMap<>();
        scopeNames.put(Scope.UNSPECIFIED, null);
        scopeNames.put(Scope.GLOBAL, "global");
        scopeNames.put(Scope.SESSION, "session");
        scopeNames.put(Scope.REQUEST, "request");
        scopeNames.put(Scope.WINDOW, "window");
        scopeNames.put(Scope.PROTOTYPE, "prototype");

    }

    // prevent from instantiation
    private AnnotationUtils() {}

    public static String calculateComponentName(
            NucleusComponent annotation,
            Class<?> componentClass) {

        return  annotation.name(); // TODO
    }

    public static boolean isSetter(ExecutableElement element){

        return (element.getModifiers().contains(Modifier.PUBLIC)) &&
                (element.getReturnType().getKind().equals(TypeKind.VOID)) &&
                (element.getSimpleName().toString().matches("^set[A-Z].*")) &&
                (element.getParameters().size() == 1);

    }

}
