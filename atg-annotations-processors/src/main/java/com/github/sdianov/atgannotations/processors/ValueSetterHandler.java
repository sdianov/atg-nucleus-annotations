package com.github.sdianov.atgannotations.processors;

import com.github.sdianov.atgannotations.CollectionOperation;
import com.github.sdianov.atgannotations.MapValue;
import com.github.sdianov.atgannotations.NucleusValue;
import com.github.sdianov.atgannotations.processors.data.PropertyRecordData;
import com.github.sdianov.atgannotations.processors.data.SetterInfo;

import java.util.Arrays;

public class ValueSetterHandler implements SetterHandler<NucleusValue> {

    private NucleusValue annotation;

    public ValueSetterHandler(NucleusValue pAnnotation) {
        this.annotation = pAnnotation;
    }

    @Override
    public PropertyRecordData processRecord(SetterInfo setterInfo) throws ProcessingException {
        final PropertyRecordData record = new PropertyRecordData();
        record.name = setterInfo.beanName;

        boolean processed = false;

        if (!"".equals(annotation.value())) {

            if (!CollectionOperation.SET.equals(annotation.operation()))
                throw new ProcessingException("simple value is incompatible with ADD or REMOVE operations ");


            // todo: check setter type
            record.values.add(annotation.value());
            processed = true;
        }

        if (annotation.list().length > 0) {
            if (processed)
                throw new ProcessingException("Only one of 'value', 'list', 'map' or 'propertyRef' is allowed ");

            // todo: check setter type is array or list

            record.operation = AnnotationUtils.operatorSymbols.get(annotation.operation());

            record.values = Arrays.asList(annotation.list());
            processed = true;
        }

        if (annotation.map().length > 0) {
            if (processed)
                throw new ProcessingException("Only one of 'value', 'list', 'map' or 'propertyRef' is allowed ");

            // todo: check setter type is Map

            record.operation = AnnotationUtils.operatorSymbols.get(annotation.operation());

            for (MapValue value : annotation.map()) {
                record.values.add(value.key() + "=" + value.value());
            }
            processed = true;
        }

        if (!"".equals(annotation.propertyRef())) {
            if (processed)
                throw new ProcessingException("Only one of 'value', 'list', 'map' or 'propertyRef' is allowed ");

            // todo: check syntax

            if (!CollectionOperation.SET.equals(annotation.operation()))
                throw new ProcessingException("simple value is incompatible with ADD or REMOVE operations ");

            record.operation = "^";

            record.values.add(annotation.propertyRef());

        }

        return record;
    }
}
