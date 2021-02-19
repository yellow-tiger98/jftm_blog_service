package com.hd.blog.service.blog;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hd.blog.entity.BlogSign;

public interface BlogSignService {

    /**
     * @Description 分页查询博文标签
     * @Param blogSign
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.hd.blog.entity.BlogSign>
     * @Author huangda
     * @Date 2021/1/20 2:18 下午
     **/
   IPage<BlogSign> queryBlogSignListPage(BlogSign blogSign);

   /**
    * @Description 查询单个博文标签信息
    * @Param signId
    * @return com.hd.blog.entity.BlogSign
    * @Author huangda
    * @Date 2021/1/20 2:19 下午
    **/
   BlogSign querybBlogSignByID(String signId);

   /**
    * @Description 新增博文标签
    * @Param blogSign
    * @return void
    * @Author huangda
    * @Date 2021/1/20 2:19 下午
    **/
   void addBlogSign(BlogSign blogSign);

   /**
    * @Description 更新博文标签
    * @Param blogSign
    * @return void
    * @Author huangda
    * @Date 2021/1/20 2:19 下午
    **/
   void updateBlogSign(BlogSign blogSign);

   /**
    * @Description 删除博文标签
    * @Param signId
    * @return void
    * @Author huangda
    * @Date 2021/1/20 2:19 下午
    **/
   void deleteBlogSign(String signId);
}
