package com.github.sdianov.atgannotations.processors;

import com.github.sdianov.atgannotations.NucleusComponent;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.NoType;
import javax.lang.model.type.TypeKind;

public class AnnotationUtils {

    public static final String ATG_GEN_OPTION = "atggen";

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
