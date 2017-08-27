package com.oauth.util;

import org.apache.catalina.util.Introspection;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yjdini on 2017/8/23.
 *
 */
public class EntityUtil {
    public static Map getAttributes(Object entity, String[] attributes) {
        if (entity == null || attributes.length == 0) {
            return null;
        }
        Map map = new HashMap();
        List attrs = Arrays.asList(attributes);
        Field[] fields = Introspection.getDeclaredFields(entity.getClass());
        for(Field field : fields) {
            if (attrs.contains(field.getName())) {
                field.setAccessible(true);
                try {
                    map.put(field.getName(), field.get(entity));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return map;
    }
}
