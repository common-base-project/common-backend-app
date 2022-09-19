package com.sipue.common.core.utils;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * @Desc
 * @Author mustang
 * @date 2022/1/8 9:35
 */
public class JsonUtil {
    public static String mapToJsonString(Map<String,Object> map){
        JSONObject jsonObject = new JSONObject(map);
        return jsonObject.toJSONString();
    }
    public static String keyValToJsonString(String k,Object v){
        Map<String,Object> map = new HashMap<>(2);
        map.put(k,String.valueOf(v));
        return mapToJsonString(map);
    }
    public static String toJson(Object obj) {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .disableHtmlEscaping()
                .create();
        return gson.toJson(obj);
    }
}