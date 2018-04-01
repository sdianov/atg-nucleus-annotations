package com.github.sdianov.atgannotations.processors.data;

import javax.lang.model.SourceVersion;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ComponentName {

    public List<String> path;
    public String name;

    private ComponentName() {

    }

    public static ComponentName fromString(final String s) {
        if (s == null || s.isEmpty()) {
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
        componentName.path = parts.subList(0, parts.size() - 2);

        return componentName;
    }

    public static ComponentName fromClassName(final String className) {
        if (className == null || className.isEmpty()) {
            throw new IllegalArgumentException("component name cannot be empty");
        }

        final String[] parts = className.split("\\.");

        final ComponentName componentName = new ComponentName();

        componentName.name = parts[parts.length - 1];
        componentName.path = Arrays.asList(parts).subList(0, parts.length - 2);

        return componentName;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("/");
        for (String part : path) {
            sb.append(part);
            sb.append("/");
        }
        sb.append(name);
        return sb.toString();
    }
}
