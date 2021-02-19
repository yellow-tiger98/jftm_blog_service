package com.hd.blog.service.search;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hd.blog.entity.Blog;

import java.util.Map;

/**
 * @InterfaceName SearchService
 * @Description
 * @Author huangda
 * @Date 2021/2/8 5:23 下午
 * @Version 1.0
 **/
public interface SearchService {

    /**
     * @Description 根据条件搜索文章
     * @Param iPage
     * @Param type
     * @Param keyWord
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.hd.blog.entity.Blog>
     * @Author huangda
     * @Date 2021/2/8 5:38 下午
     **/
    IPage<Blog> searchBlogs(IPage<Blog> iPage, String type, String keyWord);
}
