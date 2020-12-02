package com.hd.blog.config;

import com.hd.blog.handler.MyAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity //开启WebSecurity模式
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyAccessDeniedHandler myAccessDeniedHandler;

    /**
     * @description 定义请求的授权规则，也就是哪些角色分配哪些资源
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/").permitAll()
                .antMatchers("/level1/**").hasRole("role1")
                .antMatchers("/level2/**").hasRole("role2")
                .antMatchers("/level3/**").hasRole("role3")
                .anyRequest().authenticated().and();

        // 开启SpringSecurity的登录配置,如用户名字段，密码字段配置、登录页配置、登录请求配置
        http.formLogin();
//                .usernameParameter("username")
//                .passwordParameter("password")
//                .loginPage("/toLogin")
//                .loginProcessingUrl("/login");

        // 开启SpringSecurity的自动注销配置
//        http.csrf().disable(); //关闭csrf功能:跨站请求伪造,默认只能通过post方式提交logout请求
        http.logout().logoutSuccessUrl("/");

        // 配置RememberMe字段，记住我功能可以使登录在一段时间内不会失效，原理为利用cookie
        http.rememberMe().rememberMeParameter("rememberMe");

        http.exceptionHandling().accessDeniedHandler(myAccessDeniedHandler);

    }

    /**
     * @description 定义身份验证规则
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 在内存中定义验证规则，用于临时测试，实际则要连接数据库,密码的验证均需要经过加密算法处
        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                .withUser("huangda").password(new BCryptPasswordEncoder().encode("123456")).roles("role1","role2")
                .and()
                .withUser("test1").password(new BCryptPasswordEncoder().encode("123456")).roles("role1")
                .and()
                .withUser("test2").password(new BCryptPasswordEncoder().encode("123456")).roles("role2");
    }


}
