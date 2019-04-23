package com.github.sdianov.atgannotations.processors;

import com.github.sdianov.atgannotations.NucleusInject;
import com.github.sdianov.atgannotations.processors.data.ComponentDescriptor;
import com.github.sdianov.atgannotations.processors.data.PropertyRecordData;
import com.github.sdianov.atgannotations.processors.data.SetterInfo;

public class InjectSetterHandler implements SetterHandler<NucleusInject> {

    private final NucleusInject annotation;

    public InjectSetterHandler(NucleusInject pAnnotation) {
        this.annotation = pAnnotation;
    }

    @Override
    public PropertyRecordData processRecord(SetterInfo setterInfo) {
        final PropertyRecordData record = new PropertyRecordData();

        record.name = setterInfo.beanName;

        ComponentDescriptor toInject = ComponentDescriptor.fromStringOrParameterType(
                annotation.name(), setterInfo.parameterType);

        record.values.add(toInject.toString());

        return record;
    }
}
