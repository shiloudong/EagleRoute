package com.eagle.router_annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)//TYPE：类、接口、枚举、注解类型
@Retention(RetentionPolicy.CLASS)//编译class文件时生效
public @interface Route {
    String value();
}