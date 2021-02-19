package com.hd.blog.controller.web.articles;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hd.blog.common.Result;
import com.hd.blog.entity.Blog;
import com.hd.blog.service.blog.BlogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName ArticleController
 * @Description 首页模块
 * @Author huangda
 * @Date 2021/1/29 12:11 下午
 * @Version 1.0
 **/
@Slf4j
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private BlogService blogService;

    @GetMapping("/query/page")
    public String queryArticleListPage(@RequestParam Long page, @RequestParam Long limit){
        Blog blog = new Blog();
        blog.setPage(page);
        blog.setLimit(limit);
        IPage<Blog> iPage = blogService.queryBlogListPage(blog);
        return Result.success(iPage);
    }

    @GetMapping("/query/unique")
    public String queryArticleByUid(@RequestParam String uid){
        Blog blog = blogService.queryBlogByUid(uid);
        // 将MarkDown转为html 前台需要处理html格式
//        MutableDataSet options = new MutableDataSet();
//        Parser parser = Parser.builder(options).build();
//        HtmlRenderer renderer = HtmlRenderer.builder(options).build();
//        Node document = parser.parse(blog.getContent());
//        blog.setContent(renderer.render(document));
        return Result.success(blog);
    }
}
