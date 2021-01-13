package com.hd.blog.mapper.role;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hd.blog.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface RoleMapper extends BaseMapper<SysRole> {

}
