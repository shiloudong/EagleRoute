package com.eagle.router_complier;

import javax.lang.model.element.TypeElement;

public class Constant {
//    public static final ClassName ROUTER = ClassName.get("com.xsm.easy.core", "EasyRouter");

    public static final String ACTIVITY = "android.app.Activity";
    public static final String ACTIVITY_ROOT = "android.app";
    public static final String ACTIVITY_NAME = "Activity";

    public static final String ARGUMENTS_NAME = "moduleName";

    public static final String SEPARATOR = "_";
    public static final String PROJECT = "ERouter";

    public static final String METHOD_LOAD_INTO = "loadRoute";
    public static final String PACKAGE_OF_GENERATE_FILE = "com.eagle.router";
    public static final String NAME_OF_ROOT = "Router";
    public static final String INTER_ROOT = "com.eagle.router_api";
    public static final String INTER_ROOT_NAME = "IRouteLoad";

    private static final String LANG = "java.lang";
    public static final String BYTE = LANG + ".Byte";
    public static final String SHORT = LANG + ".Short";
    public static final String INTEGER = LANG + ".Integer";
    public static final String LONG = LANG + ".Long";
    public static final String FLOAT = LANG + ".Float";
    public static final String DOUBEL = LANG + ".Double";
    public static final String BOOLEAN = LANG + ".Boolean";
    public static final String STRING = LANG + ".String";
    public static final String ARRAY = "ARRAY";

    public static final String ARRAYLIST = "java.util.ArrayList";
    public static final String LIST = "java.util.List";

    public static final String BYTEARRAY = "byte[]";
    public static final String SHORTARRAY = "short[]";
    public static final String BOOLEANARRAY = "boolean[]";
    public static final String CHARARRAY = "char[]";
    public static final String DOUBLEARRAY = "double[]";
    public static final String FLOATARRAY = "float[]";
    public static final String INTARRAY = "int[]";
    public static final String LONGARRAY = "long[]";
    public static final String STRINGARRAY = "java.lang.String[]";


    public static final String NAME_OF_EXTRA = SEPARATOR + "Extra";
    public static final String NAME_OF_INTERCEPTOR = PROJECT + SEPARATOR + "Interceptor" + SEPARATOR;


}