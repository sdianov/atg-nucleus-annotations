package com.github.sdianov.atgannotations.processors.data;

import com.github.sdianov.atgannotations.NucleusComponent;

import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import java.util.Arrays;
import java.util.List;

import static com.github.sdianov.atgannotations.processors.AnnotationUtils.*;

public class ComponentName {

    private List<String> path;
    private String name;

    private ComponentName() {

    }

    public static ComponentName fromString(final String s) {
        if (isNullOrBlank(s)) {
            throw new IllegalArgumentException("component name cannot be empty");
        }
        if (!s.startsWith("/")) {
            throw new IllegalArgumentException("component name should start with '/' ");
        }

        final List<String> parts = Arrays.asList(s.split("/"));
        for (int i = 1; i <= parts.size() - 1; i++) {
            if (!SourceVersion.isIdentifier(parts.get(i))) {
                throw new IllegalArgumentException("string '" + parts.get(i) + "' is not a valid identifier ");
            }
        }

        final ComponentName componentName = new ComponentName();

        componentName.name = parts.get(parts.size() - 1);
        componentName.path = parts.subList(1, parts.size() - 1);

        return componentName;
    }

    public static ComponentName fromClassName(final String className) {
        if (isNullOrBlank(className)) {
            throw new IllegalArgumentException("component name cannot be empty");
        }

        final String[] parts = className.split("\\.");

        final ComponentName componentName = new ComponentName();

        componentName.name = parts[parts.length - 1];
        componentName.path = Arrays.asList(parts).subList(0, parts.length - 1);

        return componentName;
    }

    public static ComponentName fromStringOrClassType(
            String componentName,
            TypeElement classType) {

        if (componentName == null || "".equals(componentName)) {
            return ComponentName.fromClassName(classType.getQualifiedName().toString());
        } else {
            return ComponentName.fromString(componentName);
        }
    }

    public static ComponentName fromStringOrParameterType(
            final String string,
            final TypeElement type) {
        if (isNullOrBlank(string) && type == null) {
            throw new IllegalArgumentException("Either component name or parameter type must be provided");
        }
        if (isNullOrBlank(string)) {
            NucleusComponent annotation = type.getAnnotation(NucleusComponent.class);
            if (annotation == null || annotation.name().isEmpty())
                return fromClassName(type.toString());

            return fromString(annotation.name());

        } else {
            return fromString(string);
        }
    }

    public String getName() {
        return name;
    }

    public String fullPath() {
        StringBuilder sb = new StringBuilder();
        for (String part : path) {
            sb.append(part).append("/");
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return "/" + fullPath() + name;
    }
}
