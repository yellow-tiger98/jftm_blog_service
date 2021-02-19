package com.hd.blog.service.search.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hd.blog.constant.SysConf;
import com.hd.blog.entity.Blog;
import com.hd.blog.mapper.blog.BlogMapper;
import com.hd.blog.service.blog.BlogService;
import com.hd.blog.service.search.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @ClassName SearchServiceImpl
 * @Description
 * @Author huangda
 * @Date 2021/2/8 5:35 下午
 * @Version 1.0
 **/
@Slf4j
@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    BlogMapper blogMapper;

    @Autowired
    BlogService blogService;


    @Override
    public IPage<Blog> searchBlogs(IPage<Blog> iPage, String type, String keyWord) {

        IPage<Blog> result = null;
        LambdaQueryWrapper<Blog> queryWrapper = new QueryWrapper<Blog>().lambda();
        queryWrapper.eq(Blog::getStatus, "1");

        // 根据作者搜索
        if (SysConf.BLOG_AUTHOR.equals(type)){
            queryWrapper.eq(Blog::getAuthor, keyWord);
            result =  blogMapper.selectPage(iPage, queryWrapper);
        }

        // 根据分类搜索
        if (SysConf.BLOG_TYPE.equals(type)){
            queryWrapper.eq(Blog::getType, keyWord);
            result =  blogMapper.selectPage(iPage, queryWrapper);
        }

        // 根据标签搜索
        if (SysConf.BLOG_TAG.equals(type)){
            result =  blogMapper.searchBlogByTag(iPage, keyWord);
        }

        // 根据关键字搜索（标题、摘要、文章内容）
        if (SysConf.KEY_WORD.equals(type)){
            queryWrapper.like(Blog::getTitle, keyWord)
                    .or()
                    .like(Blog::getSummary, keyWord)
                    .or()
                    .like(Blog::getContent, keyWord);
            result = blogMapper.selectPage(iPage, queryWrapper);
        }
        if (result.getRecords().size() > 0){
            blogService.dealData(result.getRecords());

        }
        return result;
    }
}
