package com.github.sdianov.atgannotations.processors.data;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;

public class SetterInfo {

    public String beanName;

    TypeMirror parameterTypeMirror;

    public TypeElement parameterType;

    private SetterInfo(){
    }

    private static boolean isSetter(ExecutableElement element) {

        return (element.getModifiers().contains(Modifier.PUBLIC)) &&
                (element.getReturnType().getKind().equals(TypeKind.VOID)) &&
                (element.getSimpleName().toString().matches("^set[A-Z].*")) &&
                (element.getParameters().size() == 1);

    }

    public static SetterInfo fromMethod(final ExecutableElement method, final Types typeUtils){
        final String elementName = method.getSimpleName().toString();

        if (!isSetter(method))
            return null;

        final String propertyName = elementName.substring(3, 4).toLowerCase()
                + elementName.substring(4);

        final VariableElement variable = method.getParameters().get(0);

        final SetterInfo setterInfo = new SetterInfo();
        setterInfo.beanName = propertyName;
        setterInfo.parameterTypeMirror = variable.asType();
        setterInfo.parameterType = (TypeElement) typeUtils.asElement(setterInfo.parameterTypeMirror);

        return setterInfo;

    }


}
