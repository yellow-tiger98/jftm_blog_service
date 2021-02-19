package com.hd.blog.mapper.blog;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hd.blog.entity.BlogType;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface BlogTypeMapper extends BaseMapper<BlogType> {

}
