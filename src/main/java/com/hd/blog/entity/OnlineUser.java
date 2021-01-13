package com.hd.blog.entity;

import lombok.Data;

/**
 * @ClassName OnlineUser
 * @Description 在线用户，存储于Redis
 * @Author huangda
 * @Date 2021/1/12 4:05 下午
 * @Version 1.0
 **/
@Data
public class OnlineUser {

    // 用于获取token
    private String tokenId;

    // 携带token
    private String token;

    // 用户名
    private String userName;

    // 登陆ip
    private String ipaddr;

    // 登陆位置
    private String loginLocation;

    // 使用操作系统
    private String os;

    // 使用浏览器
    private String browser;

    // 角色名称
    private String roleName;

    // 登陆时间
    private String loginTime;

    // 过期时间
    private String expireTime;

}
