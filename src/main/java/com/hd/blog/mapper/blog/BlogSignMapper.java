package com.hd.blog.mapper.blog;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hd.blog.entity.BlogSign;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface BlogSignMapper extends BaseMapper<BlogSign> {

}
