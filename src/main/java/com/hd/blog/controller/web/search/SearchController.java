package com.hd.blog.controller.web.search;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hd.blog.common.Result;
import com.hd.blog.constant.SysConf;
import com.hd.blog.entity.Blog;
import com.hd.blog.service.search.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName SearchController
 * @Description
 * @Author huangda
 * @Date 2021/2/8 5:22 下午
 * @Version 1.0
 **/
@Slf4j
@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @GetMapping("/common")
    public String searchBlog(@RequestParam Long page, @RequestParam Long limit,
                             @RequestParam(required = false) String author,@RequestParam(required = false) String tagId,
                             @RequestParam(required = false) String typeId, @RequestParam(required = false) String keywords){
        IPage<Blog> iPage = new Page<>();
        iPage.setCurrent(page);
        iPage.setSize(limit);

        IPage<Blog> result = null;

        if (author != null){
            result =  searchService.searchBlogs(iPage, SysConf.BLOG_AUTHOR, author);
        }

        if (tagId != null){
            result = searchService.searchBlogs(iPage, SysConf.BLOG_TAG, tagId);
        }

        if (typeId != null){
            result = searchService.searchBlogs(iPage, SysConf.BLOG_TYPE, typeId);
        }

        if (keywords != null){
            result = searchService.searchBlogs(iPage, SysConf.KEY_WORD, keywords);
        }

        return Result.success(result);

    }



}
