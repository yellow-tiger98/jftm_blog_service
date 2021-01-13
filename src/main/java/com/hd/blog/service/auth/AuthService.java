package com.hd.blog.service.auth;

import com.hd.blog.entity.SysUser;

import java.util.Map;

/**
 * @ClassName AuthService
 * @Description
 * @Author huangda
 * @Date 2021/1/11 2:12 下午
 * @Version 1.0
 **/
public interface AuthService {
    /**
     * @Description 登陆验证
     * @Param username
     * @Param password
     * @return java.util.Map<java.lang.String,java.lang.Object>
     * @Author huangda
     * @Date 2021/1/12 9:44 下午
     **/
    Map<String,Object> login(String username, String password);

    /**
     * @Description 登陆后获取用户信息
     * @Param userUid
     * @Param token
     * @return java.util.Map<java.lang.String,java.lang.String>
     * @Author huangda
     * @Date 2021/1/12 10:36 下午
     **/
    Map<String,Object> getUserInfo(String userUid, String token);
    void logout();

}
