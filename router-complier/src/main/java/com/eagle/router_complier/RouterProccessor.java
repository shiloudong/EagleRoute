package com.eagle.router_complier;

import com.eagle.router_annotation.Route;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.WildcardTypeName;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.ElementType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedOptions;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

//指定要监听的注解
@SupportedAnnotationTypes("com.eagle.router_annotation.Route")
@SupportedOptions("moduleName")
public class RouterProccessor extends AbstractProcessor {

    List<RouteMeta> routeMetas = new ArrayList<>();

    /**
     * 节点工具类 (类、函数、属性都是节点)
     */
    private Elements elementUtils;

    /**
     * type(类信息)工具类
     */
    private Types typeUtils;

    /**
     * 文件生成器 类/资源
     */
    private Filer filerUtils;

    private String moduleName;

    private Messager messager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        elementUtils = processingEnv.getElementUtils();//节点工具类 (类、函数、属性都是节点)
        typeUtils = processingEnv.getTypeUtils();//type(类信息)工具类
        filerUtils = processingEnv.getFiler();//文件生成器 类/资源
        messager = processingEnv.getMessager();//日志
        moduleName = processingEnv.getOptions().get("moduleName");
        messager.printMessage(Diagnostic.Kind.NOTE, "init RouterProcessor " + moduleName + " success !");
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        //被Route注解的节点集合
        if (!Utils.isEmpty(annotations)) {
            Set<? extends Element> rootElements = roundEnv.getElementsAnnotatedWith(Route.class);
            if (!Utils.isEmpty(rootElements)) {
                processorRoute(rootElements);
            }
            return true;
        }

        return false;
    }

    private void processorRoute(Set<? extends Element> rootElements) {
        //获得Activity这个类的节点信息
        TypeElement activity = elementUtils.getTypeElement(Constant.ACTIVITY);
        for (Element element : rootElements) {
            RouteMeta routeMeta;
            //类信息
            TypeMirror typeMirror = element.asType();
            Route route = element.getAnnotation(Route.class);
            if (typeUtils.isSubtype(typeMirror, activity.asType())) {
                routeMeta = new RouteMeta(RouteMeta.Type.ACTIVITY, route, element);
            }else {
                throw new RuntimeException("Just support Activity or IService Route: " + element);
            }
            routeMetas.add(routeMeta);
        }
        generRouter();
    }

    private void generRouter() {
        //创建参数类型 Map<String,Class<? extends Activity>> routes>
        ParameterizedTypeName parameterizedTypeName = ParameterizedTypeName.get(
                ClassName.get(Map.class),
                ClassName.get(String.class),
                ParameterizedTypeName.get(
                        ClassName.get(Class.class),
                        WildcardTypeName.subtypeOf(ClassName.get(Constant.ACTIVITY_ROOT,Constant.ACTIVITY_NAME))
                ));

        //参数 Map<String,Class<? extends IRouteGroup>> routes> routes
        ParameterSpec parameter = ParameterSpec.builder(parameterizedTypeName, "routes").build();

        //函数 public void loadRoute(Map<String,Class<? extends Activity>> routes> routes)
        MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder(Constant.METHOD_LOAD_INTO)
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .addParameter(parameter);
        //函数体
        for (RouteMeta routeMeta : routeMetas) {
            methodBuilder.addStatement("routes.put($S, $T.class)", routeMeta.getPath(), routeMeta.getClassName());
        }
        //生成$Root$类
        String className =  moduleName+Constant.NAME_OF_ROOT;
        TypeSpec typeSpec = TypeSpec.classBuilder(className)
                .addSuperinterface(ClassName.get(Constant.INTER_ROOT,Constant.INTER_ROOT_NAME))
                .addModifiers(Modifier.PUBLIC)
                .addMethod(methodBuilder.build())
                .build();
        try {
            JavaFile.builder(Constant.PACKAGE_OF_GENERATE_FILE, typeSpec).build().writeTo(filerUtils);
            messager.printMessage(Diagnostic.Kind.NOTE,"Generated RouteRoot：" + Constant.PACKAGE_OF_GENERATE_FILE + "." + className);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}