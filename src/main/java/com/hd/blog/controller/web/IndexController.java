package com.hd.blog.controller.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hd.blog.common.Result;
import com.hd.blog.entity.BlogSign;
import com.hd.blog.entity.WebConfig;
import com.hd.blog.service.blog.BlogSignService;
import com.hd.blog.service.webConfig.WebConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName IndexController
 * @Description 初始化相关
 * @Author huangda
 * @Date 2021/2/1 1:07 下午
 * @Version 1.0
 **/
@Slf4j
@RestController
@RequestMapping("/init")
public class IndexController {

    @Autowired
    private BlogSignService signService;

    @Autowired
    private WebConfigService webConfigService;

    @GetMapping("/query/tagList")
    public String geyTagList(){
        IPage<BlogSign> result = signService.queryBlogSignListPage(new BlogSign());
        return Result.success(result);
    }

    @GetMapping("/query/webConfig")
    public String getWebConfig(){
        WebConfig webConfig = webConfigService.getWebConfig();
        return Result.success(webConfig);
    }
}
