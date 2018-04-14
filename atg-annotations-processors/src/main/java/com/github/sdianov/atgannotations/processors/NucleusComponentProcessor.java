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
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Set;

@SupportedAnnotationTypes({
        "com.github.sdianov.atgannotations.NucleusComponent"
})
@SupportedSourceVersion(SourceVersion.RELEASE_7)
@SupportedOptions(AnnotationUtils.ATG_GEN_OPTION)
public class NucleusComponentProcessor extends AbstractProcessor {

    private void logNote(String message) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, message);
    }

    private void logError(String message) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, message);
    }

    private Types getTypeUtils() {
        return processingEnv.getTypeUtils();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations,
                           RoundEnvironment roundEnv) {

        logNote("Starting NucleusComponentProcessor...");

        PropertyFileRenderer renderer = new PropertyFileRenderer(
                processingEnv.getOptions().get(AnnotationUtils.ATG_GEN_OPTION),
                processingEnv.getFiler());

        for (TypeElement te : annotations) {
            for (Element e : roundEnv.getElementsAnnotatedWith(te)) {

                logNote(">>Found component class: " + e.toString());

                if (e instanceof TypeElement) {
                    final TypeElement typeElement = (TypeElement) e;

                    try {
                        PropertyFileData fileData = processType(typeElement);

                        if (typeElement.getAnnotation(NucleusComponent.class).isInterface()) {
                            // do not create .property file
                            continue;
                        }
                        renderer.renderFile(fileData);

                    } catch (IOException e1) {
                        logError("Error writing file: " + e1.getMessage());
                        return false;
                    } catch (ProcessingException ex) {
                        logError("Annotation processing exception: " + ex.getMessage());
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private PropertyFileData processType(TypeElement typeElement) throws ProcessingException {
        final NucleusComponent annotation = typeElement.getAnnotation(NucleusComponent.class);

        PropertyFileData fileData = new PropertyFileData();

        fileData.componentName = ComponentName.fromStringOrClassType(annotation.name(), typeElement);

        fileData.headerComments.add(fileData.componentName.toString());
        fileData.className = typeElement.getQualifiedName().toString();
        fileData.description = annotation.description();
        fileData.scope = AnnotationUtils.scopeNames.get(annotation.scope());

        fileData.rawLines = Arrays.asList(annotation.rawLines());

        for (Element element : typeElement.getEnclosedElements()) {
            if (element instanceof ExecutableElement) {
                final ExecutableElement executableElement = (ExecutableElement) element;
                final SetterInfo setter = SetterInfo.fromMethod(executableElement, getTypeUtils());

                SetterHandler handler = null;

                NucleusInject injectAnnotation = executableElement.getAnnotation(NucleusInject.class);

                if (injectAnnotation != null) {

                    if (setter == null) {
                        logError("Only setter methods can be annotated");
                        continue;
                    }
                    handler = new InjectSetterHandler(injectAnnotation);
                }

                NucleusValue valueAnnotation = executableElement.getAnnotation(NucleusValue.class);
                if (valueAnnotation != null) {
                    if (handler != null) {
                        logError("Either NucleusValue or NucleusInject annotation is allowed");
                        continue;
                    }
                    if (setter == null) {
                        logError("Only setter methods can be annotated");
                        continue;
                    }
                    handler = new ValueSetterHandler(valueAnnotation);
                }

                if (handler == null) continue;

                PropertyRecordData record = handler.processRecord(setter);

                fileData.properties.add(record);
            }
        }

        return fileData;
    }

}
