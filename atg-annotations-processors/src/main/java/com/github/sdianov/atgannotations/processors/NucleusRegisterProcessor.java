package com.github.sdianov.atgannotations.processors;

import com.github.sdianov.atgannotations.NucleusComponent;
import com.github.sdianov.atgannotations.NucleusRegister;
import com.github.sdianov.atgannotations.processors.data.ComponentDescriptor;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedOptions;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.util.Set;

@SupportedAnnotationTypes({
        "com.github.sdianov.atgannotations.NucleusRegister"
})
@SupportedSourceVersion(SourceVersion.RELEASE_6)
@SupportedOptions(AnnotationUtils.ATG_GEN_OPTION)
public class NucleusRegisterProcessor extends BaseProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        Multimap<ComponentDescriptor, String> multimap = HashMultimap.create();

        for (TypeElement annotation : annotations) {
            for (Element element : roundEnv.getElementsAnnotatedWith(annotation)) {

                if (element.getAnnotation(NucleusComponent.class) == null) {
                    logError("NucleusRegister annotation should be used with NucleusComponent");
                    return true;
                }
                NucleusRegister registerAnnotation = element.getAnnotation(NucleusRegister.class);
            }
        }


        return true;
    }
}
