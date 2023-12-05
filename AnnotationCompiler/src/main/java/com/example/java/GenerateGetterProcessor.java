package com.example.java;

import com.example.annotationmodule.GenerateGetter;
import com.example.annotationmodule.GenerateSetter;
import com.google.auto.service.AutoService;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;


@SupportedAnnotationTypes("com.example.java.GenerateGetter")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
//@AutoService(Processor.class)
public class GenerateGetterProcessor extends AbstractProcessor {
    Types typeUtils;
    Elements elementUtils;
    Filer filer;


    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        typeUtils = processingEnv.getTypeUtils();
        elementUtils = processingEnv.getElementUtils();
        filer = processingEnv.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        System.out.println("-----------------GenerateGetterProcessor process .....................");
        for (TypeElement annotation : annotations) {
            System.out.println("================annotation: " + annotation.getSimpleName());
        }
        Set<? extends Element> elementsAnnotatedWith = roundEnv.getElementsAnnotatedWith(GenerateGetter.class);
        for (Element element : elementsAnnotatedWith) {
            //只处理字段上的注解
            if (element instanceof VariableElement) {
                VariableElement variableElement = (VariableElement) element;
                String filedName = variableElement.getSimpleName().toString();
                String fieldType = variableElement.asType().toString();

//                String getterMethod = "public " + fieldType + " get" + capitalize(fieldName) + "() {\n"
//                        + "    return " + fieldName + ";\n"
//                        + "}\n";

                //生成getter方法
                String getterMethod = "public " + fieldType + " get" + capitalize(filedName) + "(){\n" +
                        "   return " + filedName + ";\n"
                        + "}\n";

                System.out.println(getterMethod);

            }
        }
        return true;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> set = new HashSet<>();
        set.add(GenerateGetter.class.getCanonicalName());
        set.add(GenerateSetter.class.getCanonicalName());
        return set;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_8;
    }

    public String capitalize(String str) {
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }
}
