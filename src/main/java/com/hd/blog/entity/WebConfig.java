package com.hd.blog.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName WebConfig
 * @Description 网站配置
 * @Author huangda
 * @Date 2021/2/1 2:04 下午
 * @Version 1.0
 **/
@Data
@TableName("jftm_web_config")
public class WebConfig implements Serializable {

    private static final long serialVersionUID = -9193859231549907908L;

    // 网站名称
    private String name;

    // 邮箱
    private String email;

    // 电话
    private String qq;

    // github地址
    private String github;

    // 微信
    private String wechat;

    // 备案号
    private String recordNum;

    // 前台展示名称
    private String showName;

    // 前台展示描述
    private String showDesc;

    // 前台关于我
    private String introduction;
}
