package com.iboot.studio.common.enumdict;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;
import java.util.Set;

/**
 * 枚举字典注解处理器
 * 用于在编译时检查@EnumDict注解的类是否实现了IEnumDict接口
 */
@SupportedAnnotationTypes("com.iboot.studio.common.enumdict.EnumDict")
@SupportedSourceVersion(SourceVersion.RELEASE_17)
public class EnumDictProcessor extends AbstractProcessor {

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (roundEnv.processingOver()) {
            return false;
        }

        for (TypeElement annotation : annotations) {
            Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(annotation);
            for (Element element : elements) {
                // 只处理枚举类型
                if (element.getKind() != ElementKind.ENUM) {
                    processingEnv.getMessager().printMessage(
                        Diagnostic.Kind.ERROR,
                        "@EnumDict注解只能用于枚举类型",
                        element
                    );
                    continue;
                }

                TypeElement typeElement = (TypeElement) element;
                if (!implementsInterface(typeElement, "com.iboot.studio.common.enumdict.IEnumDict")) {
                    processingEnv.getMessager().printMessage(
                        Diagnostic.Kind.ERROR,
                        "使用@EnumDict注解的枚举类必须实现IEnumDict接口",
                        element
                    );
                }
            }
        }
        return true;
    }

    private boolean implementsInterface(TypeElement typeElement, String interfaceName) {
        for (TypeMirror typeMirror : typeElement.getInterfaces()) {
            if (typeMirror instanceof DeclaredType) {
                DeclaredType declaredType = (DeclaredType) typeMirror;
                Element element = declaredType.asElement();
                if (element.toString().equals(interfaceName)) {
                    return true;
                }
            }
        }
        return false;
    }
} 