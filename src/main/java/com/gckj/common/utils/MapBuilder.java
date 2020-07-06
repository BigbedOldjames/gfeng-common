package com.omysoft.common.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: xiongfeng
 * @Date: 2019/7/31 10:52
 * @Desc: map工具
 * @Version 1.0
 */
public class MapBuilder {

    private static Map<String, Object> map;

    private MapBuilder() {
    }

    public static MapBuilder builder() {
        map = new HashMap<>();
        return new MapBuilder();
    }

    public MapBuilder put(String key, Object value) {
        map.put(key, value);
        return this;
    }

    public Map build() {
        return map;
    }

    /**
     * @param obj
     * @param keepNullVal 是否保持null或”“
     * @return
     */
    public static Map objectToMap(Object obj, boolean keepNullVal) {

        if (obj == null) {
            return null;
        }

        Map<String, Object> map = new HashMap();

        try {
            Field[] declaredFields = obj.getClass().getDeclaredFields();
            for (Field field : declaredFields) {
                field.setAccessible(true);
                if (keepNullVal == true) {
                    map.put(field.getName(), field.get(obj));
                } else {
                    if (field.get(obj) != null && !"".equals(field.get(obj).toString())) {
                        map.put(field.getName(), field.get(obj));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}
