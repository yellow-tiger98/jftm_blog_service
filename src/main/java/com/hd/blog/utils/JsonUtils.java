package com.hd.blog.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName JsonUtils
 * @Description Json解析工具类
 * @Author huangda
 * @Date 2021/1/11 2:06 下午
 * @Version 1.0
 **/
@Slf4j
public class JsonUtils {
    // 定义jackson对象
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * @Description 将对象转化为json
     * @Param obj
     * @return java.lang.String
     * @Author huangda
     * @Date 2021/1/11 2:07 下午
     **/
    public static String objectToJson(Object obj) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        try {
            String json = gson.toJson(obj);
            return json;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @Description 将json格式的字符串转为对象
     * @Param jsonData
     * @Param beanType
     * @return T
     * @Author huangda
     * @Date 2021/1/12 4:18 下午
     **/
    public static <T> T jsonToPojo(String jsonData, Class<T> beanType) {
        try {
            T t = MAPPER.readValue(jsonData, beanType);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
