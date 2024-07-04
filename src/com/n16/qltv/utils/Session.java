package com.n16.qltv.utils;

import java.util.HashMap;
import java.util.Map;

public class Session {
    private static Map<String, Object> data = new HashMap<>();

    public static Object get(String key) { return data.get(key); }
    public static void put(String key, Object value) { data.put(key, value); }
    public static void remove(String key) { data.remove(key); }
}
