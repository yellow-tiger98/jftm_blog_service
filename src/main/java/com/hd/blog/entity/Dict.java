package com.hd.blog.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName Dict
 * @Description
 * @Author huangda
 * @Date 2021/1/20 1:37 下午
 * @Version 1.0
 **/
@Data
public class Dict implements Serializable {

    private static final long serialVersionUID = -1412755980694319754L;

    // 标签
    String label;

    // 值
    String value;
}
