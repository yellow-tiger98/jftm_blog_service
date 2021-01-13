package com.hd.blog.service.user;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hd.blog.entity.SysUser;

import java.util.List;
import java.util.Map;


public interface UserService {

    SysUser queryUserByUserName(Map<String,Object> paraMap);

    List<SysUser> queryUsers(Map<String,Object> paraMap);

    IPage<SysUser> queryUsersByPage(IPage<SysUser> ipage , Map<String,Object> paraMap);

    void addUser(SysUser sysUser);

}
