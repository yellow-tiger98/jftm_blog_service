package com.hd.blog.service.blog.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hd.blog.common.CommonException;
import com.hd.blog.constant.SysConf;
import com.hd.blog.entity.Blog;
import com.hd.blog.entity.BlogSign;
import com.hd.blog.entity.BlogType;
import com.hd.blog.entity.OnlineUser;
import com.hd.blog.mapper.blog.BlogMapper;
import com.hd.blog.service.blog.BlogService;
import com.hd.blog.service.blog.BlogSignService;
import com.hd.blog.service.blog.BlogTypeService;
import com.hd.blog.utils.DateUtils;
import com.hd.blog.utils.JsonUtils;
import com.hd.blog.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName BlogServiceImpl
 * @Description
 * @Author huangda
 * @Date 2021/1/17 4:10 下午
 * @Version 1.0
 **/
@Slf4j
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {

    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private BlogTypeService blogTypeService;

    @Autowired
    private BlogSignService blogSignService;

    @Autowired
    RedisUtil redisUtil;


    @Override
    public IPage<Blog> queryBlogListPage( Blog blog) {
        // 构建分页参数
        IPage<Blog> iPage = new Page<>();
        iPage.setCurrent(blog.getPage());
        iPage.setSize(blog.getLimit());
        // 构建查询条件
        LambdaQueryWrapper<Blog> queryWrapper = new QueryWrapper<Blog>().lambda();
        queryWrapper.like(StringUtils.isNotBlank(blog.getTitle()), Blog::getTitle, blog.getTitle());
        queryWrapper.eq(StringUtils.isNotBlank(blog.getType()), Blog::getType, blog.getType());
        queryWrapper.eq(StringUtils.isNotBlank(blog.getSign()), Blog::getSign, blog.getSign());
        // 处理类别以及标签名称
        IPage<Blog> result = blogMapper.selectPage(iPage, queryWrapper);
        if (result.getRecords().size() > 0){
            dealData(result.getRecords());
        }
        return result;
    }


    @Override
    public Blog queryBlogByUid(String blogUid) {
        LambdaQueryWrapper<Blog> queryWrapper = new QueryWrapper<Blog>().lambda();
        queryWrapper.eq(Blog::getUid, blogUid);
        Blog blog = blogMapper.selectOne(queryWrapper);
        String[] signList = blog.getSign().split(",");
        blog.setSignList(signList);
        List<Blog> blogList = new ArrayList<>();
        blogList.add(blog);
        dealData(blogList);
        return blogList.get(0);
    }


    @Override
    public void addNewBlog(Blog blog, String token) {
        // 检验博文参数
        checkBlogParam(blog);
        // 设置博文标签
        StringBuffer signList = new StringBuffer();
        for (int i = 0; i < blog.getSignList().length; i++){
            signList.append(blog.getSignList()[i]+",");
        }
        // 去掉最后一个逗号
        signList.deleteCharAt(signList.length() - 1);
        blog.setSign(signList.toString());
        // 新增模式
        if ("add".equals(blog.getMode())){
            // 设置文章uuid
            String blogUid = UUID.randomUUID().toString().replace("-","");
            blog.setUid(blogUid);
            // 获取当前在线用户
            String currentUserJson = redisUtil.get("LOGIN_TOKEN_KEY:"+token);
            OnlineUser currentUser = JsonUtils.jsonToPojo(currentUserJson, OnlineUser.class);
            blog.setAuthor(currentUser.getUserName());
            // 推送
            if ("publish".equals(blog.getOpr())){
                blog.setStatus("1");
                blogMapper.insert(blog);
            }
            // 保存为草稿
            if ("save".equals(blog.getOpr())){
                blog.setStatus("0");
                blogMapper.insert(blog);
            }
        }
        // 编辑模式
        if ("edit".equals(blog.getMode())){
            LambdaQueryWrapper<Blog> queryWrapper = new QueryWrapper<Blog>().lambda();
            queryWrapper.eq(Blog::getUid , blog.getUid());
            if ("publish".equals(blog.getOpr())){
                blog.setStatus("1");
                blog.setUpdateDate(DateUtils.getNowTime());
                blogMapper.update(blog,queryWrapper);
            }
            if ("save".equals(blog.getOpr())){
                blog.setStatus("0");
                blog.setUpdateDate(DateUtils.getNowTime());
                blogMapper.update(blog,queryWrapper);
            }
        }
    }


    @Override
    public void updateBlog(Blog blog) {
        blog.setUpdateDate(DateUtils.getNowTime());
        LambdaQueryWrapper<Blog> queryWrapper = new QueryWrapper<Blog>().lambda();
        queryWrapper.eq(Blog::getUid, blog.getUid());
        blogMapper.update(blog, queryWrapper);
    }


    @Override
    public void deleteBlog(Blog blog) {
        LambdaQueryWrapper<Blog> queryWrapper = new QueryWrapper<Blog>().lambda();
        queryWrapper.eq(Blog::getUid, blog.getUid());
        blogMapper.delete(queryWrapper);
    }

    @Override
    public List getSortList() {
        // 先从Redis中获取
        String monthSet = redisUtil.get(SysConf.MONTH_SET);
        // 如果不为空，直接返回结果
        if (StringUtils.isNotEmpty(monthSet)){
            List list = JsonUtils.jsonArrayToArrayList(monthSet);
            return list;
        }
        LambdaQueryWrapper<Blog> queryWrapper = new QueryWrapper<Blog>().lambda();
        queryWrapper.eq(Blog::getStatus,"1");
        queryWrapper.orderByDesc(Blog::getCreateDate);
        List<Blog> blogs = blogMapper.selectList(queryWrapper);
        // 处理结果，查询标签、名称等信息
        dealData(blogs);
        // 按照时间整理博客
        Map<String, List<Blog>> map = new HashMap<>();
        Set<String> sortMonth = new TreeSet<>();
        for (Blog blog : blogs){
            Date createDate = DateUtils.str2Date(blog.getCreateDate());
            String month = new SimpleDateFormat("yyyy年MM月").format(createDate).toString();
            sortMonth.add(month);
            // 按照日期分类文章集合
            if (map.get(month) == null){
                List<Blog> blogList = new ArrayList<>();
                blogList.add(blog);
                map.put(month, blogList);
            }else{
                List<Blog> blogList = map.get(month);
                blogList.add(blog);
                map.put(month, blogList);
            }
        }

        // 时间对应文章列表遍历存入Redis
        map.forEach((key, value)->{
          redisUtil.set(SysConf.BLOG_SORT_BY_MONTH+SysConf.REDIS_SEGMENTATION+key,JsonUtils.objectToJson(value).toString());
        });

        // 归档列表存入redis
        redisUtil.set(SysConf.MONTH_SET, JsonUtils.objectToJson(sortMonth).toString());

        // 将set转为list
        List list = JsonUtils.jsonArrayToArrayList(JsonUtils.objectToJson(sortMonth).toString());
        return list;
    }

    @Override
    public List<Blog> getBlogListByMonth(String monthDate) {
        if (StringUtils.isBlank(monthDate)){
            throw new CommonException("归档日期为空");
        }
        // 先从redis中看有没有数据
        String redisData = redisUtil.get(SysConf.BLOG_SORT_BY_MONTH+SysConf.REDIS_SEGMENTATION+monthDate);
        if (StringUtils.isNotEmpty(redisData)){
            List<Blog> list = (List<Blog>) JsonUtils.jsonArrayToArrayList(redisData);
            return list;
        }
        // 如果redis中没有则直接查询
        LambdaQueryWrapper<Blog> queryWrapper = new QueryWrapper<Blog>().lambda();
        queryWrapper.eq(Blog::getStatus,"1");
        queryWrapper.orderByDesc(Blog::getCreateDate);
        List<Blog> blogs = blogMapper.selectList(queryWrapper);
        // 处理结果，查询标签、名称等信息
        dealData(blogs);
        // 按照时间整理博客
        Map<String, List<Blog>> map = new HashMap<>();
        Set<String> sortMonth = new TreeSet<>();
        for (Blog blog : blogs){
            Date createDate = DateUtils.str2Date(blog.getCreateDate());
            String month = new SimpleDateFormat("yyyy年MM月").format(createDate).toString();
            sortMonth.add(month);
            // 按照日期分类文章集合
            if (map.get(month) == null){
                List<Blog> blogList = new ArrayList<>();
                blogList.add(blog);
                map.put(month, blogList);
            }else{
                List<Blog> blogList = map.get(month);
                blogList.add(blog);
                map.put(month, blogList);
            }
        }

        // 时间对应文章列表遍历存入Redis
        map.forEach((key, value)->{
            redisUtil.set(SysConf.BLOG_SORT_BY_MONTH+SysConf.REDIS_SEGMENTATION+key,JsonUtils.objectToJson(value).toString());
        });

        // 归档列表存入redis
        redisUtil.set(SysConf.MONTH_SET, JsonUtils.objectToJson(sortMonth).toString());
        return map.get(monthDate);
    }

    @Override
    public List getBlogTypeList() {
        // 先从Redis中获取
        String typeSet = redisUtil.get(SysConf.TYPE_SET);
        // 如果不为空，直接返回结果
        if (StringUtils.isNotEmpty(typeSet)){
            List<BlogType> list = (List<BlogType>) JsonUtils.jsonArrayToArrayList(typeSet);
            return list;
        }
        // 查询出设置的所有类型
        List<BlogType> blogTypeList = blogTypeService.queryBlogTypeListPage(new BlogType()).getRecords();

        // 分类列表存入redis
        redisUtil.set(SysConf.TYPE_SET, JsonUtils.objectToJson(blogTypeList).toString());

        return blogTypeList;
    }

    @Override
    public IPage<Blog> getBlogListByType(Blog blog) {
        // 构建分页参数
        IPage<Blog> iPage = new Page<>();
        iPage.setCurrent(blog.getPage());
        iPage.setSize(blog.getLimit());

        // 遍历所有文章，并按照文章类别进行分组
        LambdaQueryWrapper<Blog> queryWrapper = new QueryWrapper<Blog>().lambda();
        queryWrapper.eq(Blog::getStatus,"1");
        queryWrapper.eq(Blog::getType,blog.getType());
        queryWrapper.orderByDesc(Blog::getCreateDate);
        IPage<Blog> result = blogMapper.selectPage(iPage, queryWrapper);
        // 处理结果，查询标签、名称等信息
        dealData(result.getRecords());
        return result;
    }

    /**
     * @Description 检验上传文章参数是否合法
     * @Param blog
     * @return void
     * @Author huangda
     * @Date 2021/1/17 4:50 下午
     **/
    private void checkBlogParam(Blog blog){
        if (StringUtils.isBlank(blog.getTitle())){
            throw new CommonException("博文标题不能为空!");
        }
        if (StringUtils.isBlank(blog.getType())){
            throw new CommonException("博文类别不能为空!");
        }
        if (blog.getSignList().length == 0){
            throw new CommonException("博文标签不能为空!");
        }
    }

    /**
     * @Description 处理分页查询数据
     * @Param list
     * @return java.util.List<com.hd.blog.entity.Blog>
     * @Author huangda
     * @Date 2021/1/28 4:24 下午
     **/
    @Override
    public void dealData(List<Blog> list){
        // 先查处所有的分类以及标签，避免频繁的数据库连接
        IPage<BlogType> blogTypes = blogTypeService.queryBlogTypeListPage(new BlogType());
        IPage<BlogSign> blogSigns = blogSignService.queryBlogSignListPage(new BlogSign());
        List<BlogType> blogTypeList = blogTypes.getRecords();
        List<BlogSign> blogSignList = blogSigns.getRecords();
        // 遍历处理
        for (Blog blog : list){
            // 设置分类名称 lambda表达式 + stream流 筛选出符合条件项
            String typeName = blogTypeList.stream()
                    .filter(BLogType -> (BLogType.getTypeId().equals(blog.getType())))
                    .collect(Collectors.toList()).get(0).getTypeNa();
            blog.setTypeName(typeName);

            // 设置标签名称
            List<String> signNameList = new ArrayList<>();
            String signName;
            if (!blog.getSign().contains(",")){
                signName = blogSignList.stream()
                        .filter(BlogSign -> (BlogSign.getSignId().equals(blog.getSign())))
                        .collect(Collectors.toList()).get(0).getSignNa();
                signNameList.add(signName);
                blog.setSignListName(signNameList);
            }else{
                String[] signArr = blog.getSign().split(",");
                for (int i=0; i<signArr.length;i++){
                    int index = i;
                    signName = blogSignList.stream()
                            .filter(BlogSign -> (BlogSign.getSignId().equals(signArr[index])))
                            .collect(Collectors.toList()).get(0).getSignNa();
                    signNameList.add(signName);
                }
                blog.setSignListName(signNameList);
            }

        }

    }
}
