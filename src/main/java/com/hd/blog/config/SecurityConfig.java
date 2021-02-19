package com.hd.blog.config;

import com.hd.blog.handler.security.JwtAuthenticationEntryPoint;
import com.hd.blog.handler.security.JwtAuthenticationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity // 开启WebSecurity模式
@EnableGlobalMethodSecurity(prePostEnabled = true) // 方法请求前和请求后都进行验证
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private UserDetailsService userDetailsService;


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public JwtAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
        return new JwtAuthenticationTokenFilter();
    }

    /**
     * @Description 注册过滤器
     * @Param filter
     * @return org.springframework.boot.web.servlet.FilterRegistrationBean
     * @Author huangda
     * @Date 2021/1/13 3:02 下午
     **/
    @Bean
    public FilterRegistrationBean registrationBean(JwtAuthenticationTokenFilter filter) {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean(filter);
        registrationBean.setEnabled(false);
        return registrationBean;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }


    /**
     * @description 定义请求的授权规则，也就是哪些角色分配哪些资源
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //因为SpringSecurity使用X-Frame-Options防止网页被Frame(劫持)。所以需要关闭为了让后端的接口管理的swagger页面正常显示
        http.headers().frameOptions().disable();

        http
                //新加入,允许跨域
                .cors()
                .and()
                // 由于使用的是JWT，我们这里不需要csrf，csrf(跨站请求伪造,post请求需要携带csrf_token)
                .csrf().disable()
                // 异常的处理器，将执行未鉴权的处理方法
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and()
                // 基于token，不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                //.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                // 允许对于网站静态资源的无授权访问
                .antMatchers(
                        "/swagger-ui.html",
                        "/swagger-ui/*",
                        "/swagger-resources/**",
                        "/v2/api-docs",
                        "/v3/api-docs",
                        "/webjars/**",
                        "/actuator/**",
                        "/druid/**"
                ).permitAll()
                // 对于获取token的RestApi要允许匿名访问
                .antMatchers("/auth/**",
                        "/picture/**",
                        "/creatCode/**",
                        "/file/**",
                        "/article/**",
                        "/init/**",
                        "/sort/**",
                        "/type/**",
                        "/search/**"
                ).permitAll()
                // 除上面外的所有请求全部需要鉴权认证
                .anyRequest().authenticated();

        // 使用自定义拦截器
        // JwtAuthenticationTokenFilter: JWT认证过滤器,验证token有效性
        // UsernamePasswordAuthenticationFilter: 认证操作全靠这个过滤器
        http.addFilterBefore(registrationBean(new JwtAuthenticationTokenFilter()).getFilter(),
                UsernamePasswordAuthenticationFilter.class);

        // 禁用缓存
        http.headers().cacheControl();

    }


    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                // 设置UserDetailsService
                .userDetailsService(this.userDetailsService)
                // 使用BCrypt进行密码的hash
                .passwordEncoder(bCryptPasswordEncoder());
        //remember me
        authenticationManagerBuilder.eraseCredentials(false);
    }

    /**
     * @description 定义身份验证规则
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }


}
