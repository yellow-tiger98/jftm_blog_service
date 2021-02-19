package com.hd.blog.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName BlogSign
 * @Description
 * @Author huangda
 * @Date 2021/1/20 2:08 下午
 * @Version 1.0
 **/
@Data
@TableName("jftm_blog_sign")
public class BlogSign implements Serializable {

    private static final long serialVersionUID = -3833333964199617860L;

    // 标签编号
    private String signId;

    // 标签名称
    private String signNa;

    // 当前页
    @TableField(exist = false)
    public Long page;

    // 每页数量
    @TableField(exist = false)
    public Long limit;
}
