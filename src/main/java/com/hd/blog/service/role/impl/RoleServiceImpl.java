package com.hd.blog.service.role.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hd.blog.entity.SysRole;
import com.hd.blog.mapper.role.RoleMapper;
import com.hd.blog.service.role.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName RoleServiceImpl
 * @Description
 * @Author huangda
 * @Date 2021/1/11 3:27 下午
 * @Version 1.0
 **/
@Slf4j
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, SysRole> implements RoleService {

    @Autowired
    RoleMapper roleMapper;


    @Override
    public SysRole queryRoleByRoleID(String roleId) {
        LambdaQueryWrapper<SysRole> queryWrapper = new QueryWrapper<SysRole>().lambda();
        queryWrapper.eq(SysRole::getRoleId, roleId);
        return roleMapper.selectOne(queryWrapper);
    }
}
