package com.hd.blog.service.blog.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hd.blog.entity.BlogType;
import com.hd.blog.mapper.blog.BlogTypeMapper;
import com.hd.blog.service.blog.BlogTypeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName BlogTypeServiceImpl
 * @Description
 * @Author huangda
 * @Date 2021/1/20 2:28 下午
 * @Version 1.0
 **/
@Slf4j
@Service
public class BlogTypeServiceImpl extends ServiceImpl<BlogTypeMapper, BlogType> implements BlogTypeService {

    @Autowired
    private BlogTypeMapper blogTypeMapper;

    @Override
    public IPage<BlogType> queryBlogTypeListPage(BlogType blogType) {
        IPage<BlogType> iPage = new Page<>();
        LambdaQueryWrapper<BlogType> queryWrapper = new QueryWrapper<BlogType>().lambda();
        queryWrapper.like(StringUtils.isNotBlank(blogType.getTypeNa()), BlogType::getTypeNa, blogType.getTypeNa());
        queryWrapper.orderByAsc(BlogType::getTypeId);
        // 非分页查询
        if (blogType.getPage() != null && blogType.getLimit() != null){
            iPage.setCurrent(blogType.getPage());
            iPage.setSize(blogType.getLimit());
            return blogTypeMapper.selectPage(iPage, queryWrapper);
        }
        return iPage.setRecords(blogTypeMapper.selectList(queryWrapper));
    }

    @Override
    public BlogType querybBlogTypeByID(String typeId) {
        return null;
    }

    @Override
    public void addBlogType(BlogType blogType) {

    }

    @Override
    public void updateBlogType(BlogType blogType) {

    }

    @Override
    public void deleteBlogType(String TypeId) {

    }
}
