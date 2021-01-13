package com.hd.blog.service.role;

import com.hd.blog.entity.SysRole;

/**
 * @ClassName RoleService
 * @Description
 * @Author huangda
 * @Date 2021/1/11 3:25 下午
 * @Version 1.0
 **/
public interface RoleService {

    SysRole queryRoleByRoleID(String roleId);

}
