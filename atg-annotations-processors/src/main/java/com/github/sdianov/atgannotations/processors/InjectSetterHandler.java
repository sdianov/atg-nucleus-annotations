package com.github.sdianov.atgannotations.processors;

import com.github.sdianov.atgannotations.processors.data.PropertyRecordData;

import javax.lang.model.element.ExecutableElement;

public class InjectSetterHandler implements SetterHandler {
    @Override
    public PropertyRecordData processRecord(ExecutableElement method) {
        return null;
    }
}
