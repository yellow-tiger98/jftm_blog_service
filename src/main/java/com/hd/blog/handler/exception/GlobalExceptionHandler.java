package com.hd.blog.handler.exception;

import com.hd.blog.common.CommonException;
import com.hd.blog.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    public GlobalExceptionHandler(){
        super();
        log.info("全局异常处理器加载完毕");
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public String error(Exception e){
        log.error("Exception异常",e);
        return Result.error("系统异常，请查看日志");
    }

    @ExceptionHandler(CommonException.class)
    @ResponseBody
    public String error(CommonException e){
        log.error("通用异常",e);
        return Result.error(e.getMessage());
    }


}
