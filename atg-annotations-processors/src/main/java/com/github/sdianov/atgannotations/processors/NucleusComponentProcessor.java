package com.github.sdianov.atgannotations.processors;

import com.github.sdianov.atgannotations.NucleusComponent;
import com.github.sdianov.atgannotations.NucleusInject;
import com.github.sdianov.atgannotations.NucleusValue;
import com.github.sdianov.atgannotations.processors.data.ComponentName;
import com.github.sdianov.atgannotations.processors.data.PropertyFileData;
import com.github.sdianov.atgannotations.processors.data.PropertyRecordData;
import com.github.sdianov.atgannotations.processors.data.SetterInfo;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.util.Arrays;
import java.util.Set;

import static com.github.sdianov.atgannotations.processors.AnnotationUtils.*;

@SupportedAnnotationTypes({
        "com.github.sdianov.atgannotations.NucleusComponent"
})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedOptions(AnnotationUtils.ATG_GEN_OPTION)
public class NucleusComponentProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations,
                           RoundEnvironment roundEnv) {

        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE,
                "Starting NucleusComponentProcessor...");

        PropertyFileRenderer renderer = new PropertyFileRenderer(
                processingEnv.getOptions().get(AnnotationUtils.ATG_GEN_OPTION));

        for (TypeElement te : annotations) {
            for (Element e : roundEnv.getElementsAnnotatedWith(te)) {

                System.out.println(">>Found component class: " + e.toString());
                if (e instanceof TypeElement) {
                    final TypeElement typeElement = (TypeElement) e;

                    PropertyFileData fileData = processType(typeElement);

                    try {
                        renderer.renderFile(fileData);

                    } catch (IOException e1) {
                        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR,
                                "Error writing file: " + e1.getMessage());
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private PropertyFileData processType(TypeElement typeElement) {
        final NucleusComponent annotation = typeElement.getAnnotation(NucleusComponent.class);

        PropertyFileData fileData = new PropertyFileData();

        if ("".equals(annotation.name())) {
            fileData.componentName = ComponentName.fromClassName(typeElement.getQualifiedName().toString());
        } else {
            fileData.componentName = ComponentName.fromString(annotation.name());
        }

        fileData.headerComments.add(fileData.componentName.toString());
        fileData.className = typeElement.getQualifiedName().toString();
        fileData.description = annotation.description();
        fileData.scope = AnnotationUtils.scopeNames.get(annotation.scope());

        fileData.rawLines = Arrays.asList(annotation.rawLines());

        for (Element element : typeElement.getEnclosedElements()) {
            if (element instanceof ExecutableElement) {

                ExecutableElement executableElement = (ExecutableElement) element;

                NucleusInject inject = element.getAnnotation(NucleusInject.class);
                NucleusValue value = element.getAnnotation(NucleusValue.class);

                if (inject == null && value == null) {
                    continue;
                }

                final SetterInfo setter = SetterInfo.fromMethod(executableElement);

                if (setter == null) {
                    processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR,
                            "Only setter methods can be annotated");
                } else {

                    if (inject != null && value != null) {
                        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR,
                                "Either NucleusValue or NucleusInject is allowed");
                    } else {

                        final PropertyRecordData record = new PropertyRecordData();
                        if (inject != null) {
                            record.name = setter.beanName;

                            TypeElement paramType = (TypeElement) processingEnv.getTypeUtils().asElement(setter.parameterType);

                            record.value = ComponentName.fromStringOrParameterType(
                                    inject.name(), paramType).toString();
                        } else {
                            record.name = setter.beanName;
                            record.value = value.value();
                        }
                        fileData.properties.add(record);
                    }
                }
            }
        }

        return fileData;
    }

}
