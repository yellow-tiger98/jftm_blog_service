package com.hd.blog.service.blog;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hd.blog.entity.Blog;

import java.util.List;
import java.util.Set;


/**
 * @ClassName BlogService
 * @Description
 * @Author huangda
 * @Date 2021/1/17 3:57 下午
 * @Version 1.0
 **/
public interface BlogService {

    /**
     * @Description
     * @Param paraMap
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.hd.blog.entity.Blog>
     * @Author huangda
     * @Date 2021/1/17 4:00 下午
     **/
    IPage<Blog> queryBlogListPage(Blog blog);

    /**
     * @Description
     * @Param blogUid
     * @return com.hd.blog.entity.Blog
     * @Author huangda
     * @Date 2021/1/17 4:00 下午
     **/
    Blog queryBlogByUid(String blogUid);

    /**
     * @Description 增加博文（更新也在此方法）
     * @Param blog
     * @Param token
     * @return void
     * @Author huangda
     * @Date 2021/1/17 9:50 下午
     **/
    void addNewBlog(Blog blog, String token);

    /**
     * @Description 更新博文（状态）
     * @Param paraMap
     * @return void
     * @Author huangda
     * @Date 2021/1/17 4:01 下午
     **/
    void updateBlog(Blog blog);

    /**
     * @Description 删除博文
     * @Param paraMap
     * @return void
     * @Author huangda
     * @Date 2021/1/17 4:01 下午
     **/
    void deleteBlog(Blog blog);

    /**
     * @Description 获取归档列表
     * @Param
     * @return java.util.List
     * @Author huangda
     * @Date 2021/2/1 3:28 下午
     **/
    List getSortList();

    /**
     * @Description 获取归档列表
     * @Param monthDate
     * @return java.util.List<com.hd.blog.entity.Blog>
     * @Author huangda
     * @Date 2021/2/1 5:02 下午
     **/
    List<Blog> getBlogListByMonth(String monthDate);

    /**
     * @Description 获取分类列表
     * @Param
     * @return java.util.List
     * @Author huangda
     * @Date 2021/2/1 6:15 下午
     **/
    List getBlogTypeList();

    /**
     * @Description 根据分类获取文章列表
     * @Param typeId
     * @return java.util.List<com.hd.blog.entity.Blog>
     * @Author huangda
     * @Date 2021/2/1 6:16 下午
     **/
    IPage<Blog> getBlogListByType(Blog blog);

    /**
     * @Description  处理查询结果
     * @Param list
     * @return void
     * @Author huangda
     * @Date 2021/2/8 9:34 下午
     **/
    void dealData(List<Blog> list);
}
