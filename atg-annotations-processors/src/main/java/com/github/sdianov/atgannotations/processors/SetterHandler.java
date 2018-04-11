package com.github.sdianov.atgannotations.processors;

import com.github.sdianov.atgannotations.processors.data.PropertyRecordData;
import com.github.sdianov.atgannotations.processors.data.SetterInfo;

import javax.lang.model.element.ExecutableElement;

public interface SetterHandler {

    PropertyRecordData processRecord(ExecutableElement method);

}
