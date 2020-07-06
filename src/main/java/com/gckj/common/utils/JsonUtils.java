package com.gckj.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/** 
 * @Description：json转换工具
 * @author：ldc
 * date：2020-06-23
 */
public class JsonUtils {

    /**
     * 传入对象转换成json格式字符串
     * @param object
     * @return
     */
    public static String toString(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        String s = null;
        try {
            s = mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return s;
    }
}
