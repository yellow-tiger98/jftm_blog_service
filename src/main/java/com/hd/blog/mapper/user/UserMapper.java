package com.hd.blog.mapper.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hd.blog.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface UserMapper extends BaseMapper<SysUser> {

}
