package com.hd.blog.mapper.blog;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hd.blog.entity.Blog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface BlogMapper extends BaseMapper<Blog> {

    IPage<Blog> searchBlogByTag(IPage<Blog> iPage, @Param("tagId") String tag);

    IPage<Blog> searchBlogByKeyWord(IPage<Blog> iPage, @Param("keywords") String keyWord);
}
