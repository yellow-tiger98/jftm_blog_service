package com.hd.blog;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hd.blog.entity.SysUser;
import com.hd.blog.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class BlogApplicationTests {

    @Autowired
    private UserService userService;

    @Autowired
    DataSource dataSource;


    @Test
    void contextLoads() throws SQLException {
        System.out.println(dataSource.getClass());
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
        DruidDataSource druidDataSource = (DruidDataSource) dataSource;
        System.out.println("druidDataSource 数据源最大连接数："+druidDataSource.getMaxActive());
        System.out.println("druidDataSource 数据源初始化连接数："+druidDataSource.getInitialSize());
        connection.close();
    }

    @Test
    void testDao(){
        Map<String,Object> map = new HashMap<>();
        map.put("username","huangda");
        SysUser user = userService.queryUserByUserName(map);
        if (user!=null){
            System.out.println(user.getUsername());
        }else{
            System.out.println("查询无结果");
        }
    }

    @Test
    void testList(){
        Map<String,Object> map = new HashMap<>();
        map.put("username","huangda");
        SysUser user = userService.queryUserByUserName(map);
        if (user!=null){
            System.out.println(user.getUsername());
        }else{
            System.out.println("查询无结果");
        }
    }

    @Test
    void testPage(){
        Map<String,Object> map = new HashMap<>();
        IPage<SysUser> iPage = new Page<>();
        IPage<SysUser> user = userService.queryUsersByPage(iPage,map);
        if (user!=null){
//            User user1 = user.getRecords().get(0);
//            System.out.println(user1.getUsername());
        }else{
            System.out.println("查询无结果");
        }
    }

}
