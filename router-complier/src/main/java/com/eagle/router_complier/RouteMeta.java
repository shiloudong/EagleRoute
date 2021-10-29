package com.eagle.router_complier;

import com.eagle.router_annotation.Route;
import com.squareup.javapoet.ClassName;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

public class RouteMeta {

    public ClassName getClassName() {
       return ClassName.get(((TypeElement)element ));
    }

    public enum Type {
        ACTIVITY
    }

    private Type type;

    /**
     * 节点（Activity）
     */
    private Element element;
    /**
     * 注解使用的类对象
     */
    private Class<?> destination;

    /**
     * 路由地址
     */
    private String path;


    public RouteMeta() {
    }

    public RouteMeta(Type type, Route route, Element element) {
        this(type, element, null, route.value());
    }

    public RouteMeta(Type type, Element element, Class<?> destination, String path) {
        this.type = type;
        this.destination = destination;
        this.element = element;
        this.path = path;
    }


    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Element getElement() {
        return element;
    }

    public void setElement(Element element) {
        this.element = element;
    }

    public Class<?> getDestination() {
        return destination;
    }

    public void setDestination(Class<?> destination) {
        this.destination = destination;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


}