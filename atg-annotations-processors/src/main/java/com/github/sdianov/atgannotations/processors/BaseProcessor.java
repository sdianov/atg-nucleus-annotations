package com.github.sdianov.atgannotations.processors;

import org.apache.commons.io.FilenameUtils;

import javax.annotation.processing.AbstractProcessor;
import javax.lang.model.element.Element;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import java.io.IOException;

abstract class BaseProcessor extends AbstractProcessor {

    private String generationPath = null;

    protected void logNote(String message) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, message);
    }

    protected void logError(String message) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, message);
    }

    protected Types getTypeUtils() {
        return processingEnv.getTypeUtils();
    }

    protected String defaultGenerationPath() throws IOException {
        FileObject fileObject = processingEnv.getFiler().createResource(
                StandardLocation.CLASS_OUTPUT, "", "~tmp",
                (Element[]) null);
        String projectPath = fileObject.toUri().getPath();
        fileObject.delete();
        return FilenameUtils.concat(projectPath, "../../genconfig");
    }

    public String getGenerationPath() {
        if (generationPath == null) {

            generationPath = processingEnv.getOptions().get(AnnotationUtils.ATG_GEN_OPTION);

            if (generationPath == null || generationPath.trim().isEmpty()) {
                try {
                    generationPath = defaultGenerationPath();
                } catch (IOException e) {
                    logError("Cannot create file: " + e.getMessage());
                }
            }
        }

        return generationPath;
    }
}
