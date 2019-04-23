package com.github.sdianov.atgannotations.processors;

import com.github.sdianov.atgannotations.processors.data.PropertyRecordData;
import com.github.sdianov.atgannotations.processors.data.SetterInfo;

import java.lang.annotation.Annotation;

public interface SetterHandler<T extends Annotation> {

    PropertyRecordData processRecord(SetterInfo setterInfo) throws ProcessingException;

}
