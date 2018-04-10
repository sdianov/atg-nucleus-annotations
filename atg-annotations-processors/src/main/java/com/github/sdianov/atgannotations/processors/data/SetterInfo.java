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

    public static SetterInfo fromMethod(ExecutableElement method){
        final String elementName = method.getSimpleName().toString();

        final boolean isSetter = (method.getModifiers().contains(Modifier.PUBLIC)) &&
                (method.getReturnType().getKind().equals(TypeKind.VOID)) &&
                (elementName.matches("^set[A-Z].*")) &&
                (method.getParameters().size() == 1);

        if (!isSetter)
            return null;

        final String propertyName = elementName.substring(3, 4).toLowerCase()
                + elementName.substring(4);

        final VariableElement variable = method.getParameters().get(0);

        return new SetterInfo(propertyName, variable.asType());

    }


}
