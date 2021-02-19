package com.hd.blog.constant;

/**
 * @ClassName SysConf
 * @Description 系统常量
 * @Author huangda
 * @Date 2021/2/1 3:45 下午
 * @Version 1.0
 **/
public class SysConf {


    /**
     *  Redis用
     */

    //  redis分割符
    public final static String REDIS_SEGMENTATION = ":";

    // ---------- 归档相关 begin -----------

    // 归档时间集合KEY
    public final static String MONTH_SET = "MONTH_SET";

    // 归档时间对应文章列表key
    public final static String BLOG_SORT_BY_MONTH = "BLOG_SORT_BY_MONTH";

    // ---------- 归档相关 end -----------

    // ---------- 分类相关 begin -----------

    // 分类集合key
    public final static String TYPE_SET = "TYPE_SET" ;

    // 分类对应文章列表key
    public final static String BLOG_SORT_BY_TYPE = "BLOG_SORT_BY_TYPE";

    // ---------- 分类相关 end -----------

    /**
     *  搜索用
     */

    public final static String BLOG_AUTHOR = "BLOG_AUTHOR";

    public final static String BLOG_TAG = "BLOG_TAG";

    public final static String BLOG_TYPE = "BLOG_TYPE";

    public final static String KEY_WORD = "KEY_WORD";
}
