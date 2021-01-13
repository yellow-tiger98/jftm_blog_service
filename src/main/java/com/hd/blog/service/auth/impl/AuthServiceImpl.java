package com.hd.blog.service.auth.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hd.blog.entity.Audience;
import com.hd.blog.entity.OnlineUser;
import com.hd.blog.entity.SysRole;
import com.hd.blog.entity.SysUser;
import com.hd.blog.mapper.user.UserMapper;
import com.hd.blog.service.auth.AuthService;
import com.hd.blog.service.role.RoleService;
import com.hd.blog.utils.DateUtils;
import com.hd.blog.utils.JsonUtils;
import com.hd.blog.utils.JwtTokenUtil;
import com.hd.blog.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName AuthServiceImpl
 * @Description 登陆验证业务逻辑实现类
 * @Author huangda
 * @Date 2021/1/11 2:17 下午
 * @Version 1.0
 **/
@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private Audience audience;

    @Value(value = "${tokenHead}")
    private String tokenHead;

    @Autowired
    UserMapper userMapper;

    @Autowired
    RoleService roleService;


    @Override
    public Map<String,Object> login(String username, String password) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("msg","");
        resultMap.put("user","");
        // 校验账户或密码是否为空
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)){
            log.error("用户名或密码不能为空");
            resultMap.put("msg","用户名或密码不能为空");
            return resultMap;
        }

        // 根据用户名查询出用户
        LambdaQueryWrapper<SysUser> queryWrapper = new QueryWrapper<SysUser>().lambda();
        queryWrapper.eq(SysUser::getUsername, username);
        SysUser sysUser = userMapper.selectOne(queryWrapper);
        if (sysUser == null){
            log.error("用户名不存在");
            resultMap.put("msg", "用户名不存在");
            return resultMap;
        }
        // 默认采用sha256+随机盐
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        // 验证密码
        boolean isPassword = passwordEncoder.matches(password, sysUser.getPassword());
        if (!isPassword){
                log.error("密码不正确");
                resultMap.put("msg", "密码不正确");
                return resultMap;
        }
        // 获取用户角色(暂时支持单个)
        SysRole role = roleService.queryRoleByRoleID(sysUser.getRole());
        String roleName = role.getRoleName();
        long expiration = audience.getExpiresSecond();
        // 生成token令牌
        String jwtToken = jwtTokenUtil.createJWT(
                sysUser.getUsername(),
                sysUser.getUsername(),
                roleName,
                audience.getClientId(),
                audience.getName(),
                expiration * 1000,
                audience.getBase64Secret());
        String token = tokenHead + jwtToken;

        sysUser.setToken(token);
        sysUser.setPassword("");
        sysUser.setSysRole(role);
        sysUser.setTokenUid(UUID.randomUUID().toString().replace("-",""));
        resultMap.put("user",sysUser);
        // 添加在线用户信息到redis
        addOnlineUser(sysUser, expiration);
        return resultMap;
    }

    @Override
    public Map<String, Object> getUserInfo(String userUid, String token) {
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("token", token);
        LambdaQueryWrapper<SysUser> queryWrapper = new QueryWrapper<SysUser>().lambda();
        queryWrapper.eq(SysUser::getUsername, userUid);
        SysUser sysUser = userMapper.selectOne(queryWrapper);
        // 获取用户角色姓名
        List<String> roleList = new ArrayList<>();
        SysRole role = roleService.queryRoleByRoleID(sysUser.getRole());
        String roleName = role.getRoleName();
        roleList.add(roleName);
        resultMap.put("name", sysUser.getUsername());
        resultMap.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        resultMap.put("roles", roleList);
        return resultMap;
    }

    /**
     * @Description 登陆验证成功后，添加在线用户
     * @Param sysUser
     * @Param expirationSecond
     * @return void
     * @Author huangda
     * @Date 2021/1/12 9:44 下午
     **/
    private void addOnlineUser(SysUser sysUser, Long expirationSecond) {
        // 在线用户信息仅存于redis，不进行持久化
        OnlineUser onlineUser = new OnlineUser();
        onlineUser.setLoginTime(DateUtils.getNowTime());
        onlineUser.setUserName(sysUser.getUsername());
        onlineUser.setRoleName(sysUser.getSysRole().getRoleName());
        onlineUser.setExpireTime(DateUtils.getDateStr(new Date(), expirationSecond));
        // 以token为主键存取用户信息
        redisUtil.setEx("LOGIN_TOKEN_KEY:"+sysUser.getToken(), JsonUtils.objectToJson(onlineUser), expirationSecond, TimeUnit.SECONDS);
        // 以tokenUid为主键存取token
        redisUtil.setEx("LOGIN_UUID_KEY:"+sysUser.getTokenUid(), sysUser.getToken(), expirationSecond, TimeUnit.SECONDS);
    }

    @Override
    public void logout() {

    }
}
