package com.hd.blog.controller.admin.blog;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hd.blog.common.Result;
import com.hd.blog.entity.Blog;
import com.hd.blog.service.blog.BlogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


/**
 * @ClassName BlogController
 * @Description
 * @Author huangda
 * @Date 2021/1/17 4:55 下午
 * @Version 1.0
 **/
@Slf4j
@RestController
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @PostMapping("/query/page")
    public String queryBlogListPage(@RequestBody(required = false) Blog blog){
        IPage<Blog> result =  blogService.queryBlogListPage(blog);
        return Result.success(result);
    }

    @GetMapping("/query/unique")
    public String queryBlogByUid(@RequestParam String blogUid){
        Blog blog = blogService.queryBlogByUid(blogUid);
        return Result.success(blog);
    }

    @PostMapping("/add")
    public String addNewBlog(HttpServletRequest request, @RequestBody Blog blog){
        String token = request.getAttribute("token").toString();
        blogService.addNewBlog(blog, token);
        return Result.success();
    }

    @PutMapping("/update")
    public String updateBlog(@RequestBody Blog blog){
        blogService.updateBlog(blog);
        return Result.success();
    }

    @DeleteMapping("/delete")
    public String deleteBlog(@RequestBody Blog blog){
        blogService.deleteBlog(blog);
        return Result.success();
    }
}
