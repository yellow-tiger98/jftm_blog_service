package com.hd.blog.handler.security;

import com.hd.blog.entity.Audience;
import com.hd.blog.entity.OnlineUser;
import com.hd.blog.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName JwtAuthenticationTokenFilter
 * @Description JWT权限认证过滤器，用于验证token的有效性
 * @Author huangda
 * @Date 2021/1/12 2:38 下午
 * @Version 1.0
 **/
@Slf4j
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private Audience audience;

    @Resource
    UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value(value = "${tokenHead}")
    private String tokenHead;

    @Value(value = "${tokenHeader}")
    private String tokenHeader;

    // token过期时间
    @Value(value = "${audience.expiresSecond}")
    private Long expiresSecond;

    // token刷新时间
    @Value(value = "${audience.refreshSecond}")
    private Long refreshSecond;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        // 获取请求头信息
        String authHeader = request.getHeader(tokenHeader);

        // 请求头  'Authorization'：tokenHead + token
        if (authHeader != null && authHeader.startsWith(tokenHead)){
            log.info("传递过来的token为：{}", authHeader);

            final String token = authHeader.substring(tokenHead.length());

            //获取私钥
            String base64Secret = audience.getBase64Secret();

            //获取当前在线的用户信息
            String onlineUser = redisUtil.get("LOGIN_TOKEN_KEY:"+authHeader);

            //如果当前有对应token的登陆用户 且 token没有过期,看需不需要刷新token
            if (StringUtils.isNotEmpty(onlineUser) && !jwtTokenUtil.isExpiration(token, base64Secret)){
                // 获取token的过期时间
                Date expirationDate = jwtTokenUtil.getExpiration(token, base64Secret);
                long nowMills = System.currentTimeMillis();
                Date nowDate = new Date(nowMills);
                // 对比两个日期的时间差 秒为单位
                Integer survivalSecond = DateUtils.getSecondByTwoDay(expirationDate, nowDate);
                // 当存活时间小于刷新时间，生成新的token到客户端，同时重制新的过期时间
                if (survivalSecond < refreshSecond){

                    // 生成新的token
                    String newToken = tokenHead + jwtTokenUtil.refreshToken(token, base64Secret, expiresSecond*1000);

                    // 将生成的新token,发送到客户端
                    CookieUtils.setCookie("Admin-Token", newToken, expiresSecond.intValue());
                    OnlineUser newOnlineUser = JsonUtils.jsonToPojo(onlineUser, OnlineUser.class);

                    // 获取旧的TokenUid,用于删除redis中的旧用户
                    String oldTokenUid = newOnlineUser.getTokenId();
                    // 随机生成一个TokenUid,用于获取Token令牌
                    String tokenUid = UUID.randomUUID().toString().replace("-","");
                    newOnlineUser.setTokenId(tokenUid);
                    newOnlineUser.setToken(newToken);
                    newOnlineUser.setExpireTime(DateUtils.getDateStr(new Date(), expiresSecond));
                    newOnlineUser.setLoginTime(DateUtils.getNowTime());

                    // 从redis中移除旧token 和 对应tokenuid的用户
                    redisUtil.delete("LOGIN_TOKEN_KEY:"+authHeader);
                    redisUtil.delete("LOGIN_UUID_KEY:"+oldTokenUid);

                    // 将新的Token存入redis中
                    redisUtil.setEx("LOGIN_TOKEN_KEY:"+newToken, JsonUtils.objectToJson(newOnlineUser), expiresSecond, TimeUnit.SECONDS);
                    // 维护uuid - token 互相转换 用于查看当前在线用户对应的token
                    redisUtil.setEx("LOGIN_UUID_KEY:"+tokenUid, newToken, expiresSecond, TimeUnit.SECONDS);
                }

            }else{
                chain.doFilter(request, response);
                return;
            }
            String username = jwtTokenUtil.getUsername(token, base64Secret);
            // 这里userUid 就是 username
            String userUid = jwtTokenUtil.getUserUid(token, base64Secret);

            // 把userUid存储到request中
            request.setAttribute("userUid", userUid);
            request.setAttribute("userName", username);
            request.setAttribute("token", authHeader);
            log.info("解析出来用户:{}", username);
            log.info("解析出来用户UID:{}", userUid);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null){
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                // 校验token的有效性
                if (jwtTokenUtil.validateToken(token, userDetails, base64Secret)){
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities()
                    );
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        chain.doFilter(request, response);
    }
}
