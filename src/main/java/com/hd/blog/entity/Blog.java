package com.hd.blog.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hd.blog.common.CommonEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName Blog
 * @Description 文章实体
 * @Author huangda
 * @Date 2021/1/17 2:55 下午
 * @Version 1.0
 **/
@Data
@TableName("jftm_blog")
public class Blog implements Serializable {

    private static final long serialVersionUID = -3687606954729956206L;

    // 文章封面
    private String cover;
    // 文章uid
    private String uid;
    // 文章标题
    private String title;
    // 文章作者
    private String author;
    // 文章分类
    private String type;
    // 文章标签
    private String sign;
    // 文章内容
    private String content;
    // 文章创建时间
    private String createDate;
    // 文章更新时间
    private String updateDate;
    // 文章状态 （0：草稿，1：已上架，2：已下架）
    private String status;
    // 文章摘要
    private String summary;

    // 标签列表
    @TableField(exist = false)
    private String[] signList;

    // 分类名称
    @TableField(exist = false)
    private String typeName;

    // 标签列表名称
    @TableField(exist = false)
    private List signListName;

    // 模式 (add 新增 , edit 编辑)
    @TableField(exist = false)
    private String mode;

    // 操作（publish 发布 ， save 保存）
    @TableField(exist = false)
    private String opr;

    // 当前页
    @TableField(exist = false)
    public Long page;

    // 每页数量
    @TableField(exist = false)
    public Long limit;
}
