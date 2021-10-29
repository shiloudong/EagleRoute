package com.eagle.router_complier;

import java.util.Collection;
import java.util.Map;

public class Utils {

    public static boolean isEmpty(String str){
        return str == null || str.equals("") || str.isEmpty();
    }

    public static boolean isEmpty(Collection<?> coll) {
        return coll == null || coll.isEmpty();
    }

    public static boolean isEmpty(final Map<?, ?> map) {
        return map == null || map.isEmpty();
    }
}