package com.hd.blog.handler.security;

import com.hd.blog.entity.SysRole;
import com.hd.blog.entity.SysUser;
import com.hd.blog.service.role.RoleService;
import com.hd.blog.service.user.UserService;
import com.hd.blog.utils.SecurityUserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MyUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Map<String,Object> map = new HashMap<>();
        map.put("username",username);
        SysUser user  = userService.queryUserByUserName(map);
        if (user == null){
            throw new UsernameNotFoundException(String.format("用户'%s'不存在",username));
        }
        // 获取用户角色列表
        List<String> roleNameList = new ArrayList<>();
        SysRole sysRole = roleService.queryRoleByRoleID(user.getRole());
        roleNameList.add(sysRole.getRoleName());
        user.setRoleNames(roleNameList);
        return SecurityUserUtils.createSecurityUser(user);
    }
}
