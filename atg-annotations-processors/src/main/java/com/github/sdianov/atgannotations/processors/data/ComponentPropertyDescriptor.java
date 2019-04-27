package com.github.sdianov.atgannotations.processors.data;

public class ComponentPropertyDescriptor {

    private final ComponentDescriptor component;

    private final String name;

    public ComponentPropertyDescriptor(ComponentDescriptor component, String name) {
        this.component = component;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ComponentDescriptor getComponent() {
        return component;
    }
}
