package com.hd.blog.controller.admin.blog;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hd.blog.common.Result;
import com.hd.blog.entity.BlogSign;
import com.hd.blog.service.blog.BlogSignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName BlogSignController
 * @Description
 * @Author huangda
 * @Date 2021/1/20 4:11 下午
 * @Version 1.0
 **/
@Slf4j
@RestController
@RequestMapping("/blogSign")
public class BlogSignController {

    @Autowired
    private BlogSignService blogSignService;

    @PostMapping("/query/page")
    public String queryBlogSignListPage(@RequestBody(required = false) BlogSign blogSign){
        if (blogSign == null){
            blogSign = new BlogSign();
        }
        IPage<BlogSign> iPage = blogSignService.queryBlogSignListPage(blogSign);
        return Result.success(iPage);
    }
}
