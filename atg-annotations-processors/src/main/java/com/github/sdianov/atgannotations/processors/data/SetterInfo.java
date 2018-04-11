package com.github.sdianov.atgannotations.processors.data;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

public class SetterInfo {

    public String beanName;

    public TypeMirror parameterType;

    private SetterInfo(String pBeanName, TypeMirror pParameterType){
        this.beanName = pBeanName;
        this.parameterType = pParameterType;
    }

    public static boolean isSetter(ExecutableElement element) {

        return (element.getModifiers().contains(Modifier.PUBLIC)) &&
                (element.getReturnType().getKind().equals(TypeKind.VOID)) &&
                (element.getSimpleName().toString().matches("^set[A-Z].*")) &&
                (element.getParameters().size() == 1);

    }

    public static SetterInfo fromMethod(ExecutableElement method){
        final String elementName = method.getSimpleName().toString();

        if (!isSetter(method))
            return null;

        final String propertyName = elementName.substring(3, 4).toLowerCase()
                + elementName.substring(4);

        final VariableElement variable = method.getParameters().get(0);

        return new SetterInfo(propertyName, variable.asType());

    }


}
