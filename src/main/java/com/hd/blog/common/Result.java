package com.hd.blog.common;

import com.hd.blog.utils.JsonUtils;
import lombok.Data;

@Data
public class Result {

    private String code;
    private String msg;
    private Object data;

    /**
     * @Description  成功返回
     * @Param data
     * @return java.lang.String
     * @Author huangda
     * @Date 2021/1/11 2:10 下午
     **/
    public static String success(Object data){
        Result result = new Result();
        result.setCode("500");
        result.setMsg("操作成功");
        result.setData(data);
        return JsonUtils.objectToJson(result);
    }

    /**
     * @Description 错误返回
     * @Param msg
     * @return java.lang.String
     * @Author huangda
     * @Date 2021/1/11 2:10 下午
     **/
    public static String error(String msg){
        Result result = new Result();
        result.setCode("300");
        result.setMsg(msg);
        return JsonUtils.objectToJson(result);
    }
}
