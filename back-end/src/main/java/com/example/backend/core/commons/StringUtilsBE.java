package com.example.backend.core.commons;

import java.util.List;

public class StringUtilsBE {
    public static final String EMPTY = "";

    public static String ifNullToEmpty( String source ) {
        return source == null ? EMPTY : source;
    }

    public static boolean isNotNullOrEmpty( String value ) {
        return value != null && !EMPTY.equals(value.trim());
    }

    public static boolean isNullOrEmpty( String str ) {
        return str == null || EMPTY.equals(str.trim());
    }

    public static boolean isNullOrEmpty(List<?> lst){
        return lst == null || lst.isEmpty();
    }

    public static boolean isListNotNullOrEmpty( List<?> list ) {
        return list != null && !list.isEmpty();
    }
}
