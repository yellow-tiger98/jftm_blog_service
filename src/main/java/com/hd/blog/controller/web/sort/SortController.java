package com.hd.blog.controller.web.sort;

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
 * @ClassName SortController
 * @Description 归档模块
 * @Author huangda
 * @Date 2021/2/1 3:25 下午
 * @Version 1.0
 **/
@Slf4j
@RestController
@RequestMapping("/sort")
public class SortController {

    @Autowired
    private BlogService blogService;


    @GetMapping("/getSortList")
    public String getSortList(){
        List sortList = blogService.getSortList();
        return Result.success(sortList);
    }

    @GetMapping("/getArticleByMonth")
    public String getArticleByMonth(@RequestParam String monthDate){
        List<Blog> blogList = blogService.getBlogListByMonth(monthDate);
        return Result.success(blogList);
    }
}
