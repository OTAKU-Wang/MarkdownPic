package com.choice.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.*;

/**
 * @classDesc: 功能描述:json的工具类，依赖fastjson
 * @Author: wangmaoshuai
 * @createTime: Created in 下午3:31 18-2-26
 * @version: v1.0
 * @copyright: 北京辰森
 * @email: wms@choicesoft.com.cn
 */

public final class JSONUtil {
    private JSONUtil() {
    }


    public static String writeValueAsString(Object object) {
        try {
            return JSONObject.toJSONString(object);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONObject sortJsonObject(JSONObject obj) throws Exception {
        Map<String, Object> map = new TreeMap<>();
        for (String key : obj.keySet()) {
            Object value = obj.get(key);
            if (value instanceof JSONObject) {
                map.put(key, sortJsonObject(JSONObject.parseObject(((JSONObject) value).toJSONString())));
            } else if (value instanceof JSONArray) {
                map.put(key, sortJsonArray(JSONArray.parseArray(((JSONArray) value).toJSONString())));
            } else {
                map.put(key, value);
            }
        }
        return new JSONObject(map);
    }

    public static JSONArray sortJsonArray(JSONArray array) throws Exception {
        List<Object> list = new ArrayList<>();
        for (Object obj : array) {
            if (obj instanceof JSONObject) {
                list.add(sortJsonObject(JSONObject.parseObject(((JSONObject) obj).toJSONString())));
            } else if (obj instanceof JSONArray) {
                list.add(sortJsonArray(JSONArray.parseArray(((JSONArray) obj).toJSONString())));
            } else {
                list.add(obj);
            }
        }
        list.sort(Comparator.comparing(Object::toString));
        return new JSONArray(list);
    }

    public static boolean isJsonObject(String json) {
        try {
            JSONObject.parseObject(json);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static boolean isJsonArray(String jsonArray) {
        try {
            JSONArray.parseArray(jsonArray);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
