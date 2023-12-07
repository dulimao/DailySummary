package com.example.java;

import com.example.annotationmodule.GenerateGetter;
import com.example.annotationmodule.GenerateSetter;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;


@SupportedAnnotationTypes("com.example.java.GenerateGetter")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
//@AutoService(Processor.class)
public class GenerateGetterProcessor extends AbstractProcessor {


    /**
     * Messager用来报告错误，警告和其他提示信息;
     * Filer用来创建新的源文件，class文件以及辅助文件;
     * Elements中包含用于操作Element的工具方法;
     * Types中包含用于操作类型TypeMirror的工具方法;
     */
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

        return processer(annotations,roundEnv);


//        System.out.println("-----------------GenerateGetterProcessor process .....................");
//        for (TypeElement annotation : annotations) {
//            System.out.println("================annotation: " + annotation.getSimpleName());
//        }
//        Set<? extends Element> elementsAnnotatedWith = roundEnv.getElementsAnnotatedWith(GenerateGetter.class);
//        for (Element element : elementsAnnotatedWith) {
//            System.out.println("GenerateGetter element: " + element + " element_name:  " + element.getSimpleName() + " Mirrors: " + element.getAnnotationMirrors()+ " Modifiers: " + element.getModifiers());
//            GenerateGetter annotation = element.getAnnotation(GenerateGetter.class);
//            System.out.println(element + " annotation: " + annotation.toString());
//        }
//
//        /**
//         * 打印如下
//         * ================annotation: GenerateGetter
//         * ================annotation: GenerateSetter
//         * GenerateGetter element_name:  name Mirrors: @com.example.annotationmodule.GenerateGetter Modifiers: [private]
//         * GenerateGetter element_name:  age Mirrors: @com.example.annotationmodule.GenerateGetter,@com.example.annotationmodule.GenerateSetter Modifiers: [private, static]
//         * public java.lang.String getName(){
//         *    return name;
//         * }
//         *
//         * public int getAge(){
//         *    return age;
//         * }
//         */
//        for (Element element : elementsAnnotatedWith) {
//            //只处理字段上的注解
//            if (element instanceof VariableElement) {
//                VariableElement variableElement = (VariableElement) element;
//                String filedName = variableElement.getSimpleName().toString();
//                String fieldType = variableElement.asType().toString();
//                System.out.println("variableElement type : " + variableElement.asType().getKind().toString());
//
////                String getterMethod = "public " + fieldType + " get" + capitalize(fieldName) + "() {\n"
////                        + "    return " + fieldName + ";\n"
////                        + "}\n";
//
//                //生成getter方法
//                String getterMethod = "public " + fieldType + " get" + capitalize(filedName) + "(){\n" +
//                        "   return " + filedName + ";\n"
//                        + "}\n";
//
//                System.out.println(getterMethod);
//
//            }
//        }
//        return true;

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

    public boolean processer(Set<? extends TypeElement> annotations, RoundEnvironment roundEnvironment) {
        HashMap<String, String> nameMap = new HashMap<>();

        Set<? extends Element> annotationElements = roundEnvironment.getElementsAnnotatedWith(GenerateGetter.class);

        for (Element element : annotationElements) {
            GenerateGetter annotation = element.getAnnotation(GenerateGetter.class);
            String name = annotation.name();
            nameMap.put(name, element.getSimpleName().toString());
            System.out.println("processer() " + name + "---> " + element.getSimpleName().toString());
            //nameMap.put(element.getSimpleName().toString(), name);//MainActiviy-RouteName_MainActivity
        }

        //generate Java File
        generateJavaFile(nameMap);

        return true;
    }

    /**
     * public class AnnotationRoute$Finder implements Provider {
     *   private HashMap routeMap;
     *
     *   public AnnotationRoute$Finder() {
     *     routeMap = new HashMap<>();
     *     routeMap.put("RouteName_ActivityTwo", "ActivityTwo");
     *     routeMap.put("RouteName_MainActivity", "MainActivity");
     *     routeMap.put("RouteName_ActivityThree", "ActivityThree");
     *   }
     *
     *   public String getActivityName(String routeName) {
     *     if (null != routeMap && !routeMap.isEmpty()) {
     *       return (String)routeMap.get(routeName);
     *     }
     *     return "";
     *   }
     * }
     *
     * public class Person {
     *     @GenerateGetter(name = "name")
     *     private String name;
     *     @GenerateGetter(name = "age")
     *     @GenerateSetter
     *     private static int age;
     *
     *     public String getName() {
     *         return name;
     *     }
     *
     *     public void setName(String name) {
     *         this.name = name;
     *     }
     * }
     * @param nameMap
     */


    /**
     * 生成Person.java文件   文件路径：app/build/generated/ap_generated_sources/debug/out/com/example/myandroiddemo/Person.java
     *                      文件包名+类名：com.example.myandroiddemo.Person
     * @param nameMap
     */
    public void generateJavaFile(Map<String, String> nameMap) {
        //generate Constructor method
        MethodSpec.Builder constructorMethodBuilder = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addStatement("System.out.println(\"Person ConstructorMethod\")");
        for (String key : nameMap.keySet()) {
            String name = nameMap.get(key);
            constructorMethodBuilder.addStatement("System.out.println(\"$N---$N\")",key,name);
        }
        MethodSpec constructorMethodName = constructorMethodBuilder.build();


        TypeSpec.Builder classSpecBuilder = TypeSpec.classBuilder("Person")
                .addModifiers(Modifier.PUBLIC)
                .addModifiers(Modifier.FINAL)
                .addMethod(constructorMethodName);
        //generate getter method
        for (String key : nameMap.keySet()) {
            String name = nameMap.get(key);
            MethodSpec getterName = MethodSpec.methodBuilder("get" + capitalize(name))
                    .addModifiers(Modifier.PUBLIC)
                    .returns(String.class)
                    .beginControlFlow("if (true)")
                    .addStatement("System.out.println(\"$N get method\")",name)
                    .endControlFlow()
                    .addStatement("return $N",name).build();

            ParameterSpec parameterSpec = ParameterSpec.builder(String.class,name,Modifier.TRANSIENT).build();

            MethodSpec setterName = MethodSpec.methodBuilder("set" + capitalize(name))
                    .addModifiers(Modifier.PUBLIC)
                    .addParameter(String.class,name)
                    .addStatement("this.$N = $N",name,parameterSpec.name).build();
            classSpecBuilder.addField(String.class,name,Modifier.PRIVATE);
            classSpecBuilder.addMethod(getterName);
            classSpecBuilder.addMethod(setterName);
            }


        //generate class
         TypeSpec typeSpec = classSpecBuilder.addSuperinterface(Serializable.class).build();
        JavaFile javaFile = JavaFile.builder("com.example.myandroiddemo", typeSpec).build();
        try {
            javaFile.writeTo(filer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
