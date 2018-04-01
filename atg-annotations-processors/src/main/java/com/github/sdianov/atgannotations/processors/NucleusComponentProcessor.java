package com.github.sdianov.atgannotations.processors;

import com.github.sdianov.atgannotations.NucleusComponent;
import com.github.sdianov.atgannotations.NucleusInject;
import com.github.sdianov.atgannotations.NucleusValue;
import com.github.sdianov.atgannotations.processors.data.ComponentName;
import com.github.sdianov.atgannotations.processors.data.PropertyFileData;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.util.Set;

@SupportedAnnotationTypes({
        "com.github.sdianov.atgannotations.NucleusComponent"
})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedOptions(AnnotationUtils.ATG_GEN_OPTION)
public class NucleusComponentProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations,
                           RoundEnvironment roundEnv) {

        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Starting NucleusComponentProcessor...");

        PropertyFileRenderer renderer = new PropertyFileRenderer(
                processingEnv.getOptions().get(AnnotationUtils.ATG_GEN_OPTION));


        for (TypeElement te : annotations) {
            for (Element e : roundEnv.getElementsAnnotatedWith(te)) {

                System.out.println(">>Found component class: "+ e.toString());
                if (e instanceof TypeElement) {
                    final TypeElement typeElement = (TypeElement) e;

                    PropertyFileData fileData = processType(typeElement);

                    try {
                        renderer.renderFile(fileData);

                    } catch (IOException e1) {
                        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Error writing file: " + e1.getMessage());
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

        fileData.description = annotation.description();
        fileData.scope = annotation.scope().toString();

        for (Element element : typeElement.getEnclosedElements()) {
            if (element instanceof ExecutableElement) {

                ExecutableElement executableElement = (ExecutableElement) element;

                NucleusInject inject = element.getAnnotation(NucleusInject.class);
                NucleusValue value = element.getAnnotation(NucleusValue.class);

                if (((inject != null) || (value != null)) && (!AnnotationUtils.isSetter(executableElement))) {
                    processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Only setter methods can be annotated");
                }

            }
        }

        return fileData;
    }

}
