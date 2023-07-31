package com.n16.qltv.vendor;

import java.util.HashMap;
import java.util.Map;

public class Session {
    private Map<String, Object> data = new HashMap<>();

    public Session() { }

    public Object get(String key) { return this.data.get(key); }
    public void put(String key, Object value) { this.data.put(key, value); }
    public void remove(String key) { this.data.remove(key); }
}
