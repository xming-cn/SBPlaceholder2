package com.xming.sbplaceholder2.common;

public class ArrayUtils {
    private ArrayUtils() {}
    public static String toString(Object[] arr) {
        return toString(arr, null);
    }
    public static String toString(Object[] arr, String nullStr) {
        if (arr == null) return "null";
        if (arr.length == 0) return "[]";
        String string = arr[0] != null ? arr[0].toString() : "";
        StringBuilder builder = new StringBuilder("[" + string);
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] == null) {
                if (nullStr != null)    builder.append(", null");
            }
            else builder.append(", ").append(arr[i].toString());
        }
        builder.append("]");
        return builder.toString();
    }
}
