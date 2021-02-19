package com.hd.blog.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;
import java.util.List;

@Data
@TableName("blog_user")
public class SysUser implements Serializable {

    private static final long serialVersionUID = 2630721451777784966L;

    // 用户名
    private String username;

    // 密码
    private String password;

    // 角色
    private String role;

    // 状态
    private String status;

    // 头像
    private String avatar;

    // 角色对象
    @TableField(exist = false)
    private SysRole sysRole;

    // 身份令牌
    @TableField(exist = false)
    private String token;

    // 角色名称集合
    @TableField(exist = false)
    private List<String> roleNames;

    // 用于获取token身份令牌
    @TableField(exist = false)
    private String tokenUid;


}
