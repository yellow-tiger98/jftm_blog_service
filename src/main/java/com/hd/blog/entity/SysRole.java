package com.hd.blog.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @ClassName SysRole
 * @Description
 * @Author huangda
 * @Date 2021/1/11 3:19 下午
 * @Version 1.0
 **/
@Data
@TableName("blog_role")
public class SysRole {
    private String roleId;

    private String roleName;

    private String roleDesc;
}
