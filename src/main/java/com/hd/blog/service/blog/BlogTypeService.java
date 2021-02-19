package com.hd.blog.service.blog;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hd.blog.entity.BlogType;

public interface BlogTypeService {

    /**
     * @Description 分页查询博文分类
     * @Param blogType
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.hd.blog.entity.BlogType>
     * @Author huangda
     * @Date 2021/1/20 2:18 下午
     **/
    IPage<BlogType> queryBlogTypeListPage(BlogType blogType);

    /**
     * @Description 查询单个博文分类信息
     * @Param typeId
     * @return com.hd.blog.entity.BlogType
     * @Author huangda
     * @Date 2021/1/20 2:19 下午
     **/
    BlogType querybBlogTypeByID(String typeId);

    /**
     * @Description 新增博文分类
     * @Param blogType
     * @return void
     * @Author huangda
     * @Date 2021/1/20 2:19 下午
     **/
    void addBlogType(BlogType blogType);

    /**
     * @Description 更新博文分类
     * @Param blogType
     * @return void
     * @Author huangda
     * @Date 2021/1/20 2:19 下午
     **/
    void updateBlogType(BlogType blogType);

    /**
     * @Description 删除博文分类
     * @Param TypeId
     * @return void
     * @Author huangda
     * @Date 2021/1/20 2:19 下午
     **/
    void deleteBlogType(String TypeId);
}
