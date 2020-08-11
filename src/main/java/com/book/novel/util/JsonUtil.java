package com.book.novel.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @Author: liu
 * @Date: 2020/8/11
 * @Description: java对象和json互转工具类
 */
public class JsonUtil {

    private static ObjectMapper om = new ObjectMapper();

    public static String toJson(Object o) {
        try {
            return om.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object toObject(String json, Class valueType) {
        try {
            return om.readValue(json, valueType);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
