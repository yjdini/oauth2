package com.ini.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Somnus`L on 2017/5/11.
 *
 */
public class MapBuilder {
    private final HashMap<String, Object> map;
    private Map<String, Object> result;
    /**
     * static factory method
     *
     */
    public static MapBuilder instance() {
        return new MapBuilder();
    }

    public static MapBuilder ok() {
        MapBuilder mapBuilder = new MapBuilder();
        mapBuilder.map.put("status", "ok");
        return mapBuilder;
    }

    public static MapBuilder error() {
        MapBuilder mapBuilder = new MapBuilder();
        mapBuilder.map.put("status", "error");
        return mapBuilder;
    }

    public static MapBuilder error(String message) {
        MapBuilder mapBuilder = new MapBuilder();
        mapBuilder.map.put("status", "error");
        mapBuilder.map.put("message", message);

        return mapBuilder;
    }

    public MapBuilder setMessage(String message) {
        this.map.put("message", message);
        return this;
    }

    public MapBuilder put(String key, Object value) {
        this.map.put(key, value);
        return this;
    }

    public MapBuilder result(String key, Object value) {
        if(result == null) {
            result = new HashMap<>();
            this.map.put("result", result);
        }
        result.put(key, value);
        return this;
    }

    private MapBuilder() {
        this.map = new HashMap<>();
    }

    public HashMap<String, Object> getMap() {
        return map;
    }
}
