package com.hd.blog.controller.admin.blog;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hd.blog.common.Result;
import com.hd.blog.entity.BlogType;
import com.hd.blog.service.blog.BlogTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName BlogTypeController
 * @Description
 * @Author huangda
 * @Date 2021/1/20 3:33 下午
 * @Version 1.0
 **/
@Slf4j
@RestController
@RequestMapping("/blogType")
public class BlogTypeController {

    @Autowired
    private BlogTypeService blogTypeService;

    @PostMapping("/query/page")
    public String queryBlogTypeListPage(@RequestBody(required = false)BlogType blogType){
        if (blogType == null){
            blogType = new BlogType();
        }
        IPage<BlogType> iPage = blogTypeService.queryBlogTypeListPage(blogType);
        return Result.success(iPage);
    }
}
