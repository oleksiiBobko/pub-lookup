package com.pub.lookup.util;

public class PubUtils {
    public static String normalize(String string) {
        return string.replaceAll("[^A-Za-z0-9]", "");
    }
}
