package com.hd.blog.service.blog.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hd.blog.entity.BlogSign;
import com.hd.blog.mapper.blog.BlogSignMapper;
import com.hd.blog.service.blog.BlogSignService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName BlogSignServiceImpl
 * @Description
 * @Author huangda
 * @Date 2021/1/20 2:31 下午
 * @Version 1.0
 **/
@Slf4j
@Service
public class BlogSignServiceImpl extends ServiceImpl<BlogSignMapper, BlogSign> implements BlogSignService {
    
    @Autowired
    private BlogSignMapper blogSignMapper;

    @Override
    public IPage<BlogSign> queryBlogSignListPage(BlogSign blogSign) {
        IPage<BlogSign> iPage = new Page<>();
        LambdaQueryWrapper<BlogSign> queryWrapper = new QueryWrapper<BlogSign>().lambda();
        queryWrapper.like(StringUtils.isNotBlank(blogSign.getSignNa()), BlogSign::getSignNa, blogSign.getSignNa());
        queryWrapper.orderByAsc(BlogSign::getSignId);
        // 非分页查询
        if (blogSign.getPage() != null && blogSign.getLimit() != null){
            iPage.setCurrent(blogSign.getPage());
            iPage.setSize(blogSign.getLimit());
            return blogSignMapper.selectPage(iPage, queryWrapper);
        }
        return iPage.setRecords(blogSignMapper.selectList(queryWrapper));
    }

    @Override
    public BlogSign querybBlogSignByID(String signId) {
        return null;
    }

    @Override
    public void addBlogSign(BlogSign blogSign) {

    }

    @Override
    public void updateBlogSign(BlogSign blogSign) {

    }

    @Override
    public void deleteBlogSign(String signId) {

    }
}