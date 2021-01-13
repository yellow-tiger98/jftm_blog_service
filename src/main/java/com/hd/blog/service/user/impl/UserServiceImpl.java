package com.hd.blog.service.user.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hd.blog.entity.SysUser;
import com.hd.blog.mapper.user.UserMapper;
import com.hd.blog.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, SysUser> implements UserService {

    @Autowired
    private UserMapper userMapper;

    public SysUser queryUserByUserName(Map<String,Object> paraMap){
        LambdaQueryWrapper<SysUser> queryWrapper = new QueryWrapper<SysUser>().lambda();
        queryWrapper.eq(SysUser::getUsername , paraMap.get("username"));
        SysUser user = userMapper.selectOne(queryWrapper);
        return user;
    }

    @Override
    public List<SysUser> queryUsers(Map<String, Object> paraMap) {
        LambdaQueryWrapper<SysUser> queryWrapper = new QueryWrapper<SysUser>().lambda();
        queryWrapper.eq(StringUtils.isNotBlank(String.valueOf(paraMap.get("username"))), SysUser::getUsername , paraMap.get("username"));
        List<SysUser> lists = userMapper.selectList(queryWrapper);
        return lists;
    }

    @Override
    public IPage<SysUser> queryUsersByPage(IPage<SysUser> ipage, Map<String, Object> paraMap) {
        LambdaQueryWrapper<SysUser> queryWrapper = new QueryWrapper<SysUser>().lambda();
//        queryWrapper.eq(StringUtils.isNotEmpty(String.valueOf(paraMap.get("username"))),User::getUsername , paraMap.get("username"));
        IPage<SysUser> users = userMapper.selectPage(ipage, queryWrapper);
        return users;
    }

    @Override
    @Transactional
    public void addUser(SysUser sysUser) {

    }


}
