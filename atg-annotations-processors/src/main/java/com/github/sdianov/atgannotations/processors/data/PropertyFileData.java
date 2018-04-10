package com.github.sdianov.atgannotations.processors.data;

import java.util.ArrayList;
import java.util.List;

public class PropertyFileData {

    public ComponentName componentName;

    public List<String> headerComments = new ArrayList<>();

    public String className;

    public String scope;

    public String description;

    public List<String> rawLines = null;

    public List<PropertyRecordData> properties = new ArrayList<>();
}
