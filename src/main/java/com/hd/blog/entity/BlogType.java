package com.hd.blog.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName BlogType
 * @Description
 * @Author huangda
 * @Date 2021/1/20 2:06 下午
 * @Version 1.0
 **/
@Data
@TableName("jftm_blog_type")
public class BlogType implements Serializable {

    private static final long serialVersionUID = 3937478473162409449L;

    // 分类编号
    private String typeId;

    // 分类名称
    private String typeNa;

    // 当前页
    @TableField(exist = false)
    public Long page;

    // 每页数量
    @TableField(exist = false)
    public Long limit;
}
