package com.hd.blog.common;

public class CommonException extends RuntimeException {

    public CommonException(Exception e){
        super(e);
    }

    public CommonException(String msg , Exception e){
        super(msg,e);
    }

    public CommonException(String msg){
        super(msg);
    }
}
