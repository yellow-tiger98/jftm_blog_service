package com.hd.blog.controller.web.type;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hd.blog.common.Result;
import com.hd.blog.entity.Blog;
import com.hd.blog.service.blog.BlogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName TypeController
 * @Description
 * @Author huangda
 * @Date 2021/2/1 6:11 下午
 * @Version 1.0
 **/
@Slf4j
@RestController
@RequestMapping("/type")
public class TypeController {

    @Autowired
    private BlogService blogService;

    @GetMapping("/getBlogTypeList")
    public String getBlogTypeList(){
        List blogTypeList = blogService.getBlogTypeList();
        return Result.success(blogTypeList);
    }

    @GetMapping("/getBlogsByType")
    public String getBlogListByType(@RequestParam Long page, @RequestParam Long limit, @RequestParam String typeId){
        Blog blog = new Blog();
        blog.setPage(page);
        blog.setLimit(limit);
        blog.setType(typeId);
        IPage<Blog> iPage = blogService.getBlogListByType(blog);
        return Result.success(iPage);
    }
}
