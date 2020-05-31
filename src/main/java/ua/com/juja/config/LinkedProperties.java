package ua.com.juja.config;

import java.util.*;

public class LinkedProperties extends Properties {

    private Map<String, String> linkedHashMap = new LinkedHashMap<>();

    @Override
    public Set<String> stringPropertyNames() {
        return linkedHashMap.keySet();
    }

    @Override
    public String getProperty(String key) {
        return linkedHashMap.get(key);
    }

    @Override
    public Object put(Object key, Object value) {
        return linkedHashMap.put(key.toString(), value.toString());
    }
}