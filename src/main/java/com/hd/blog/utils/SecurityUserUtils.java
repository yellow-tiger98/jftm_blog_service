package com.hd.blog.utils;

import com.hd.blog.entity.SysUser;
import com.hd.blog.handler.security.SecurityUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @description 用于创建Specurity用户类
 */
public class SecurityUserUtils {

    public SecurityUserUtils(){

    }

    public static SecurityUser createSecurityUser(SysUser sysUser){
        boolean enabled = sysUser.getStatus() == "1";
        return new SecurityUser(
                sysUser.getUsername(),
                sysUser.getPassword(),
                enabled,
                mapToGrantedAuthorities(sysUser.getRoleNames())
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<String> authorities) {
        return authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

}
